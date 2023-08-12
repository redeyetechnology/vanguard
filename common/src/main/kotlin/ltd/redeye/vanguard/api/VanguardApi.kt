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

package ltd.redeye.vanguard.api

import java.net.InetAddress
import ltd.redeye.vanguard.punishment.Ban

interface VanguardApi<Player, OriginSource> {
    /**
     * Checks if the player is banned.
     *
     * @param player The player to check.
     * @return `true` if the player is banned, `false` otherwise.
     */
    fun isBanned(player: Player): Boolean

    /**
     * Gets the current ban for the player.
     *
     * @param player The player to check.
     * @return The current ban, or `null` if the player is not banned.
     */
    fun getCurrentBan(player: Player): Ban?
    fun ban(player: Player, origin: OriginSource?, reason: String?, duration: Long?): Ban
    fun unban(player: Player, origin: OriginSource?)
    fun banIp(player: Player, origin: OriginSource?, reason: String?, duration: Long?)
    fun banIp(address: InetAddress, origin: OriginSource?, reason: String?, duration: Long?)
    fun unbanIp(player: Player, origin: OriginSource?)
    fun unbanIp(address: InetAddress, origin: OriginSource?)
    fun kick(player: Player, origin: OriginSource?, reason: String?)
    fun kickAll(origin: OriginSource?, reason: String?)
    fun isMuted(player: Player): Boolean
    fun getCurrentMute(player: Player): Void
    fun mute(player: Player, origin: OriginSource?, reason: String?, duration: Long?)
    fun unmute(player: Player, origin: OriginSource?)
    fun muteIp(player: Player, origin: OriginSource?, reason: String?, duration: Long?)
    fun muteIp(address: InetAddress, origin: OriginSource?, reason: String?, duration: Long?)
    fun unmuteIp(player: Player, origin: OriginSource?)
    fun unmuteIp(address: InetAddress, origin: OriginSource?)
    fun warn(player: Player, origin: OriginSource?, reason: String?)
    fun addNote(player: Player, origin: OriginSource, note: String)
    fun addNoteIp(address: InetAddress, origin: OriginSource, note: String)
}