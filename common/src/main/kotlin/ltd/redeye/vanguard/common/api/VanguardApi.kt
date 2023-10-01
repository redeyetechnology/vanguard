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

package ltd.redeye.vanguard.common.api

import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.player.VanguardPlayer
import java.net.InetAddress
import ltd.redeye.vanguard.common.punishment.type.Ban
import ltd.redeye.vanguard.common.punishment.type.Kick
import ltd.redeye.vanguard.common.punishment.type.Mute
import ltd.redeye.vanguard.common.punishment.type.Warning
import java.util.UUID

interface VanguardApi {
    /*
     * Checks if the player is banned.
     *
     * @param player The player to check.
     * @return `true` if the player is banned, `false` otherwise.
     */
    fun isBanned(player: VanguardPlayer): Boolean

    /**
     * Gets the current ban for the player.
     *
     * @param player The player to check.
     * @return The current ban, or `null` if the player is not banned.
     */
    fun getCurrentBan(player: VanguardPlayer): Ban?
    fun ban(player: VanguardPlayer, origin: VanguardOrigin, reason: String?, duration: Long?): Ban
    fun unban(player: VanguardPlayer, origin: VanguardOrigin): Boolean
    fun banIp(player: VanguardPlayer, origin: VanguardOrigin, reason: String?, duration: Long?): Ban
    fun banIp(address: InetAddress, origin: VanguardOrigin, reason: String?, duration: Long?): Ban
    fun unbanIp(player: VanguardPlayer, origin: VanguardOrigin): Boolean
    fun unbanIp(address: InetAddress, origin: VanguardOrigin): Boolean
    fun kick(player: VanguardPlayer, origin: VanguardOrigin, reason: String?): Kick
    fun kickAll(origin: VanguardOrigin, reason: String?)
    fun isMuted(player: VanguardPlayer): Boolean
    fun getCurrentMute(player: VanguardPlayer): Mute?
    fun mute(player: VanguardPlayer, origin: VanguardOrigin, reason: String?, duration: Long?): Mute
    fun unmute(player: VanguardPlayer, origin: VanguardOrigin): Boolean
    fun muteIp(player: VanguardPlayer, origin: VanguardOrigin, reason: String?, duration: Long?): Mute?
    fun muteIp(address: InetAddress, origin: VanguardOrigin, reason: String?, duration: Long?): Mute?
    fun unmuteIp(player: VanguardPlayer, origin: VanguardOrigin): Boolean
    fun unmuteIp(address: InetAddress, origin: VanguardOrigin): Boolean
    fun warn(player: VanguardPlayer, origin: VanguardOrigin, reason: String?): Warning
    fun addNote(player: VanguardPlayer, origin: VanguardOrigin, note: String)
    fun addNoteIp(address: InetAddress, origin: VanguardOrigin, note: String)
    fun getPlayer(uuid: UUID): VanguardPlayer?
}