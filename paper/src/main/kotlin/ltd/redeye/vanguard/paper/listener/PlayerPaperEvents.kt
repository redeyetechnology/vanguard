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

package ltd.redeye.vanguard.paper.listener

import io.papermc.paper.event.player.AsyncChatEvent
import ltd.redeye.vanguard.common.VanguardCore
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class PlayerPaperEvents : Listener {
    @EventHandler
    fun preJoin(event: AsyncPlayerPreLoginEvent) {
        val preventJoin = VanguardCore.instance.playerManager.generateBanMessage(event.uniqueId) ?: return

        event.disallow(
            AsyncPlayerPreLoginEvent.Result.KICK_BANNED,
            preventJoin
        )
    }

    @EventHandler
    fun playerJoin(event: PlayerJoinEvent) {
        VanguardCore.instance.playerManager.playerJoined(
            event.player.uniqueId,
            event.player.name,
            event.player.address.hostString
        )
    }

    @EventHandler
    fun playerLeave(event: PlayerQuitEvent) {
        VanguardCore.instance.playerManager.playerLeft(
            event.player.uniqueId
        )
    }

    @EventHandler
    fun onAsyncChat(event: AsyncChatEvent) {
        val player = event.player

        val mute = VanguardCore.instance.punishmentManager.getActiveMute(
            player.uniqueId,
            VanguardCore.instance.config.serverName
        ) ?: return

        event.isCancelled = true

        val muteMessage = VanguardCore.instance.playerManager.generateMuteMessage(mute)
        player.sendMessage(muteMessage)
    }

}