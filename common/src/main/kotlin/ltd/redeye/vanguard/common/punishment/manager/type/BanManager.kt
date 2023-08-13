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

package ltd.redeye.vanguard.common.punishment.manager.type

import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.type.Ban
import java.time.Duration

interface BanManager {
    fun ban(vanguardPlayer: VanguardPlayer, reason: String?, source: VanguardOrigin?, duration: Duration?)

    fun unban(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?)

    fun banIp(vanguardPlayer: VanguardPlayer, reason: String?, source: VanguardOrigin?, duration: Duration?)

    fun unbanIp(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?)

    fun banIp(address: String, targetName: String, reason: String?, source: VanguardOrigin?, duration: Duration?)

    fun unbanIp(address: String, source: VanguardOrigin?)

    fun isBanned(vanguardPlayer: VanguardPlayer): Boolean

    fun isIpBanned(address: String): Boolean

    fun getActiveBan(vanguardPlayer: VanguardPlayer): Ban?

    fun getActiveBan(address: String): Ban?
}