/*
 * Vanguard
 * Copyright (C) 2023 RedEye Technologies Limited
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ltd.redeye.vanguard.common.network.messaging.proxy

import ltd.redeye.vanguard.common.message.VanguardMessage
import ltd.redeye.vanguard.common.message.serialization.SerializedVanguardMessage
import ltd.redeye.vanguard.common.network.NetworkManager
import ltd.redeye.vanguard.common.network.messaging.RedisChannel
import ltd.redeye.vanguard.common.network.messaging.proxy.internal.AlertPlayerMessage
import ltd.redeye.vanguard.common.network.messaging.proxy.internal.AlertStaffMessage
import ltd.redeye.vanguard.common.network.messaging.proxy.internal.KickPlayerMessage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import org.jetbrains.annotations.ApiStatus.Internal
import java.util.*
import kotlin.reflect.KClass

/**
 * A messaging proxy for when Redis connectivity is enabled
 * The RedisMessagingProxy will take serialize the messages and send them to the redis server
 * and send to the DefaultMessagingProxy for processing on the sender server.
 */
class RedisMessagingProxy(private val default: MessagingProxy): MessagingProxy {

    companion object {
        const val ALERT_PLAYER_CHANNEL = "vanguard/alert/player"
        const val KICK_PLAYER_CHANNEL = "vanguard/kick/player"
        const val ALERT_STAFF_CHANNEL = "vanguard/alert/staff"
    }

    // We instantiate this here as we do not want it being accessed elsewhere
    private val networkManager = NetworkManager()

    private val alertPlayerChannel = registerAndListen(
        ALERT_PLAYER_CHANNEL,
        AlertPlayerMessage::class
    ) { message -> default.alertPlayer(message.uuid, message.vanguardMessage) }

    private val kickPlayerChannel = registerAndListen(
        KICK_PLAYER_CHANNEL,
        KickPlayerMessage::class
    ) { message -> default.kickPlayer(message.uuid, GsonComponentSerializer.gson().deserialize(message.message)) }

    private val alertStaffChannel = registerAndListen(
        ALERT_STAFF_CHANNEL,
        AlertStaffMessage::class
    ) { message -> default.alertStaff(message.vanguardMessage) }

    override fun alertPlayer(uuid: UUID, message: VanguardMessage, placeholders: TagResolver?): Boolean {
        // Alert the player if they are on the origin server
        if(default.alertPlayer(uuid, message, placeholders)) {
            return true
        }

        val serialized = message.serialize(placeholders)
        alertPlayerChannel.send(AlertPlayerMessage(uuid, serialized))
        return true
    }

    /**
     * This method is not public API. Please send a VanguardMessage instead using [alertPlayer]
     */
    @Internal
    override fun alertPlayer(uuid: UUID, message: SerializedVanguardMessage) {
        throw IllegalAccessException("Cannot send serialized messages to RedisMessagingProxy")
    }

    override fun kickPlayer(player: UUID, message: Component): Boolean {
        // Could not kick the player from the origin server, send across redis
        if(!default.kickPlayer(player, message)) {
            val serializedMessage = GsonComponentSerializer.gson().serialize(message)
            kickPlayerChannel.send(KickPlayerMessage(player, serializedMessage))
        }
        return true
    }

    override fun alertStaff(message: VanguardMessage, placeholders: TagResolver?) {
        // Alert the staff on the origin server
        default.alertStaff(message, placeholders)

        val serialized = message.serialize(placeholders)
        alertStaffChannel.send(AlertStaffMessage(serialized))
    }

    /**
     * This method is not public API. Please send a VanguardMessage instead using [alertStaff]
     */
    @Internal
    override fun alertStaff(message: SerializedVanguardMessage) {
        throw IllegalAccessException("Cannot send serialized messages to RedisMessagingProxy")
    }

    /**
     * Registers the channel and sets up a listener for the channel
     */
    private fun <T: Any> registerAndListen(channel: String, clazz: KClass<T>, listen: (T) -> Unit): RedisChannel<T> {
        val redisChannel = networkManager.register(channel, clazz.java)
        redisChannel.listen { message ->
            listen.invoke(message.message)
        }
        return redisChannel
    }

}