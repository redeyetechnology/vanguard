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

package ltd.redeye.vanguard.common.message

import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.message.section.MessageBossBar
import ltd.redeye.vanguard.common.message.section.MessageSound
import ltd.redeye.vanguard.common.message.section.MessageTitle
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import java.io.Serializable

@ConfigSerializable
data class VanguardMessage(
    var chat: MutableList<String>? = mutableListOf(""),
    var actionbar: String?,
    var title: MessageTitle?,
    var bossbar: MessageBossBar?,
    var sound: MessageSound?
) : Serializable, VanguardMessageBag() {

    fun send(target: Audience, tagResolver: TagResolver? = null) {
        if (chat != null && chat!!.size > 0) {
            for (message in chat!!) {
                target.sendMessage(deserialize(message, tagResolver))
            }
        }

        if (actionbar != null) {
            target.sendActionBar(deserialize(actionbar!!, tagResolver))
        }

        if (title != null) {
            title!!.send(target, tagResolver)
        }

        if (bossbar != null) {
            bossbar!!.send(target, tagResolver)
        }

        if (sound != null) {
            sound!!.send(target)
        }
    }

    fun send(target: VanguardPlayer, tagResolver: TagResolver? = null) {
        send(target.audience(), tagResolver)
    }
}