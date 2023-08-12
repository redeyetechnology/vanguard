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

import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.type.Ban
import ltd.redeye.vanguard.common.punishment.type.Kick
import ltd.redeye.vanguard.common.punishment.type.Mute
import ltd.redeye.vanguard.common.punishment.type.Warning
import java.net.InetAddress
import java.util.*

class VanguardApiImpl(val core: VanguardCore) : VanguardApi {
    override fun isBanned(player: VanguardPlayer): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCurrentBan(player: VanguardPlayer): Ban? {
        TODO("Not yet implemented")
    }

    override fun ban(player: VanguardPlayer, origin: VanguardOrigin?, reason: String?, duration: Long?): Ban {
        TODO("Not yet implemented")
    }

    override fun unban(player: VanguardPlayer, origin: VanguardOrigin?): Boolean {
        TODO("Not yet implemented")
    }

    override fun banIp(player: VanguardPlayer, origin: VanguardOrigin?, reason: String?, duration: Long?): Ban {
        TODO("Not yet implemented")
    }

    override fun banIp(address: InetAddress, origin: VanguardOrigin?, reason: String?, duration: Long?): Ban {
        TODO("Not yet implemented")
    }

    override fun unbanIp(player: VanguardPlayer, origin: VanguardOrigin?): Boolean {
        TODO("Not yet implemented")
    }

    override fun unbanIp(address: InetAddress, origin: VanguardOrigin?): Boolean {
        TODO("Not yet implemented")
    }

    override fun kick(player: VanguardPlayer, origin: VanguardOrigin?, reason: String?): Kick {
        TODO("Not yet implemented")
    }

    override fun kickAll(origin: VanguardOrigin?, reason: String?) {
        TODO("Not yet implemented")
    }

    override fun isMuted(player: VanguardPlayer): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCurrentMute(player: VanguardPlayer): Mute? {
        TODO("Not yet implemented")
    }

    override fun mute(player: VanguardPlayer, origin: VanguardOrigin?, reason: String?, duration: Long?): Mute {
        TODO("Not yet implemented")
    }

    override fun unmute(player: VanguardPlayer, origin: VanguardOrigin?): Boolean {
        TODO("Not yet implemented")
    }

    override fun muteIp(player: VanguardPlayer, origin: VanguardOrigin?, reason: String?, duration: Long?): Mute? {
        TODO("Not yet implemented")
    }

    override fun muteIp(address: InetAddress, origin: VanguardOrigin?, reason: String?, duration: Long?): Mute? {
        TODO("Not yet implemented")
    }

    override fun unmuteIp(player: VanguardPlayer, origin: VanguardOrigin?): Boolean {
        TODO("Not yet implemented")
    }

    override fun unmuteIp(address: InetAddress, origin: VanguardOrigin?): Boolean {
        TODO("Not yet implemented")
    }

    override fun warn(player: VanguardPlayer, origin: VanguardOrigin?, reason: String?): Warning {
        TODO("Not yet implemented")
    }

    override fun addNote(player: VanguardPlayer, origin: VanguardOrigin, note: String) {
        TODO("Not yet implemented")
    }

    override fun addNoteIp(address: InetAddress, origin: VanguardOrigin, note: String) {
        TODO("Not yet implemented")
    }

    override fun getPlayer(uuid: UUID): VanguardPlayer? {
        TODO("Not yet implemented")
    }
}