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

import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.message.VanguardMessage
import ltd.redeye.vanguard.common.message.serialization.SerializedVanguardMessage
import ltd.redeye.vanguard.common.network.messaging.proxy.MessagingProxy
import ltd.redeye.vanguard.common.punishment.VanguardPunishmentManager
import ltd.redeye.vanguard.common.util.Permissions
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

/**
 * A messaging proxy for when no message broker is available
 */
class PaperSingleMessagingProxy(val plugin: JavaPlugin) : MessagingProxy {
    val logger = VanguardCore.instance.logger


    override fun alertPlayer(uuid: UUID, message: VanguardMessage, placeholders: TagResolver?): Boolean {
        return ifPlayerOnline(uuid) {
            message.send(it, placeholders)
        }
    }

    override fun alertPlayer(uuid: UUID, message: SerializedVanguardMessage) {
        ifPlayerOnline(uuid) {
            message.send(it)
        }
    }

    override fun kickPlayer(player: UUID, message: Component, scope: String): Boolean {
        println("Kicking player $player with message $message")

        return ifPlayerOnline(player) {
            Bukkit.getScheduler().runTask(plugin) { _ -> it.kick(message) }
        } && (scope == VanguardPunishmentManager.GLOBAL_SCOPE || VanguardCore.instance.config.serverName == scope)
    }

    override fun kickPlayer(address: String, message: Component, scope: String): Boolean {
        var kickedPlayer = false
        Bukkit.getOnlinePlayers().forEach { player ->
            if(player.address.hostString == address) {
                Bukkit.getScheduler().runTask(plugin) { _ -> player.kick(message) }
                kickedPlayer = true
            }
        }
        return kickedPlayer
    }

    override fun alertStaff(message: VanguardMessage, placeholders: TagResolver?) {
        println("Alerting staff with message $message")
        getStaff { staff -> staff.forEach { message.send(it, placeholders) } }
    }

    override fun alertStaff(message: SerializedVanguardMessage) {
        getStaff { staff -> staff.forEach { message.send(it) } }
    }

    private fun ifPlayerOnline(uuid: UUID, online: (Player) -> Unit): Boolean {
        val player = Bukkit.getPlayer(uuid)
        if (player != null) {
            online.invoke(player)
            return true
        }
        return false
    }

    private fun getStaff(staff: (List<Player>) -> Unit) {
        val players = Bukkit.getOnlinePlayers().filter { it.hasPermission(Permissions.STAFF.permission()) }
        println(players.toString())
        if (players.isNotEmpty()) {
            staff.invoke(players)
        }
    }

}