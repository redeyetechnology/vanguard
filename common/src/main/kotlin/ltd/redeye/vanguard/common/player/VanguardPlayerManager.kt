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

package ltd.redeye.vanguard.common.player

import ltd.redeye.vanguard.common.punishment.type.Ban
import ltd.redeye.vanguard.common.util.CountdownUtil
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class VanguardPlayerManager(private val core: ltd.redeye.vanguard.common.VanguardCore) {
    val serverCache = mutableMapOf<UUID, VanguardPlayer>()

    fun playerJoined(uuid: UUID, name: String, ip: String) {
        val vanguardPlayer = core.storageDriver.loadPlayer(uuid)
        vanguardPlayer.knownNames.add(name)
        vanguardPlayer.knownIps.add(ip)
        core.storageDriver.savePlayer(vanguardPlayer)
    }

    fun playerLeft(uuid: UUID) {
        if (!serverCache.containsKey(uuid)) return

        core.storageDriver.savePlayer(serverCache[uuid]!!)
        serverCache.remove(uuid)
    }

    fun generateBanMessage(address: String): Component? {
        val activeBan = core.punishmentManager.getActiveBan(address, core.config.serverName) ?: return null
        return generateBanMessage(activeBan)
    }

    fun generateBanMessage(uuid: UUID): Component? {
        val activeBan = core.punishmentManager.getActiveBan(uuid, core.config.serverName) ?: return null
        return generateBanMessage(activeBan)
    }

    fun generateBanMessage(activeBan: Ban): Component {
        val message = core.messages.disallowedLoginBanned
        val noReasonProvided = core.messages.noReasonProvided

        val originPlayer = core.playerManager.getPlayerName(activeBan.source?.uuid?.toString())

        val expiresTextRaw =
            if (activeBan.expires == Date(0)) core.messages.expiryPlaceholders.permanent
            else core.messages.expiryPlaceholders.temporary

        val formattedDate = Date.from(activeBan.expires.toInstant())
        val formattedNew = SimpleDateFormat.getInstance().format(formattedDate)
        val countdown = CountdownUtil.countdownString(Date(), activeBan.expires)

        val expiresTagResolver = TagResolver.builder()
            .tag("date", Tag.inserting(Component.text(formattedNew)))
            .tag("countdown", Tag.inserting(Component.text(countdown)))
            .build()

        val expiresTag = MiniMessage.miniMessage().deserialize(expiresTextRaw, expiresTagResolver)

        val tagResolver = TagResolver.builder()
            .tag("reason", Tag.inserting(Component.text(activeBan.reason ?: noReasonProvided)))
            .tag("origin", Tag.inserting(Component.text(originPlayer)))
            .tag("expires", Tag.inserting(expiresTag))
            .build()

        val miniMessage = MiniMessage.miniMessage()

        val components = mutableListOf<Component>()
        message.forEach {
            components.add(miniMessage.deserialize(it, tagResolver))
        }

        return Component.join(JoinConfiguration.newlines(), components)
    }

    private fun getPlayerName(source: String?): String {
        if (source == null) {
            return core.messages.unknown
        }

        return try {
            val uuid = UUID.fromString(source)
            val vanguardPlayer = core.storageDriver.loadPlayer(uuid)
            vanguardPlayer.knownNames.lastOrNull() ?: core.messages.unknown
        } catch (e: IllegalArgumentException) {
            core.messages.unknown
        }
    }

    fun getCachedOnlinePlayers(): List<VanguardPlayer> {
        return listOf()
    }

    fun isOnline(vanguardPlayer: VanguardPlayer): Boolean {
        return core.playerAdapter.isOnline(vanguardPlayer)

        // TODO: Check if player is online via Redis

    }

}