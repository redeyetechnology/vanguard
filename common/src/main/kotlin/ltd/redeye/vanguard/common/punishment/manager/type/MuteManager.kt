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
import ltd.redeye.vanguard.common.punishment.type.Mute
import java.time.Duration
import java.util.*

interface MuteManager {
    fun mute(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin,
        duration: Duration?,
        scope: String
    )

    fun unmute(vanguardPlayer: VanguardPlayer, source: VanguardOrigin, scope: String)

    fun muteIp(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin,
        duration: Duration?,
        scope: String
    )

    fun unmuteIp(vanguardPlayer: VanguardPlayer, source: VanguardOrigin, scope: String)

    fun muteIp(
        address: String,
        targetName: String,
        reason: String?,
        source: VanguardOrigin,
        duration: Duration?,
        scope: String
    )

    fun unmuteIp(address: String, source: VanguardOrigin, scope: String)

    fun isMuted(vanguardPlayer: VanguardPlayer, scope: String): Boolean

    fun isIpMuted(address: String, scope: String): Boolean

    fun getActiveMute(vanguardPlayer: VanguardPlayer, scope: String): Mute?

    fun getActiveMute(address: String, scope: String): Mute?
    fun getActiveMute(uuid: UUID, scope: String): Mute?
}