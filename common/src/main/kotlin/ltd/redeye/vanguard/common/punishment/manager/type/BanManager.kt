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
import java.util.*

interface BanManager {
    fun ban(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin,
        duration: Duration?,
        scope: String
    ) : Ban

    fun unban(vanguardPlayer: VanguardPlayer, source: VanguardOrigin, scope: String)

    fun banIp(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin,
        duration: Duration?,
        scope: String
    ) : Set<Ban>

    fun unbanIp(vanguardPlayer: VanguardPlayer, source: VanguardOrigin, scope: String)

    fun banIp(
        address: String,
        targetName: String,
        reason: String?,
        source: VanguardOrigin,
        duration: Duration?,
        scope: String
    ) : Ban

    fun unbanIp(address: String, source: VanguardOrigin, scope: String)

    fun isBanned(vanguardPlayer: VanguardPlayer, scope: String): Boolean

    fun isIpBanned(address: String, scope: String): Boolean

    fun getActiveBan(vanguardPlayer: VanguardPlayer, scope: String): Ban?

    fun getActiveBan(address: String, scope: String): Ban?
    fun getActiveBan(uuid: UUID, scope: String): Ban?
}