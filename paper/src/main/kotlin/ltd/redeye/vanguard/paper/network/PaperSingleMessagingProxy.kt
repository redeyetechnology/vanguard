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

package ltd.redeye.vanguard.paper.network

import ltd.redeye.vanguard.common.message.VanguardMessage
import ltd.redeye.vanguard.common.network.messaging.MessagingProxy
import ltd.redeye.vanguard.common.util.Permissions
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import java.util.*

/**
 * A messaging proxy for when no message broker is available
 */
object PaperSingleMessagingProxy : MessagingProxy {

    override fun alertPlayer(uuid: UUID, message: VanguardMessage) {
        val player = Bukkit.getPlayer(uuid)
        if (player != null) {
            message.send(player)
        }
    }

    override fun kickPlayer(player: UUID, message: Component) {
        Bukkit.getPlayer(player)?.kick(message)
    }

    override fun alertStaff(message: VanguardMessage) {
        Bukkit.getOnlinePlayers().forEach { player ->
            if (player.hasPermission(Permissions.STAFF.permission())) {
                message.send(player)
            }
        }
    }

}