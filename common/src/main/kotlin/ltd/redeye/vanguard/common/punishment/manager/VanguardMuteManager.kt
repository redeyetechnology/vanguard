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

package ltd.redeye.vanguard.common.punishment.manager

import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.manager.type.MuteManager
import ltd.redeye.vanguard.common.punishment.type.Mute
import java.time.Duration
import java.util.*

class VanguardMuteManager(private val core: VanguardCore) : MuteManager {
    override fun getActiveMute(uuid: UUID, scope: String): Mute? {
        return core.storageDriver.getActiveMute(uuid, scope)
    }

    override fun isMuted(vanguardPlayer: VanguardPlayer, scope: String): Boolean {
        val activeMute = getActiveMute(vanguardPlayer.uuid, scope)
        return activeMute != null
    }

    override fun isIpMuted(address: String, scope: String): Boolean {
        val activeMute = getActiveMute(address, scope)
        return activeMute != null
    }

    override fun getActiveMute(vanguardPlayer: VanguardPlayer, scope: String): Mute? {
        return getActiveMute(vanguardPlayer.uuid, scope)
    }

    override fun getActiveMute(address: String, scope: String): Mute? {
        return core.storageDriver.getActiveMute(address, scope)
    }

    override fun mute(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    ) {

        val expires: Date = if (duration != null) {
            Date.from(java.time.Instant.now().plus(duration))
        } else {
            Date(0)
        }

        val mute = Mute(
            id = UUID.randomUUID(),
            target = vanguardPlayer.uuid.toString(),
            targetName = vanguardPlayer.knownNames.first(),
            reason = reason,
            source = source.toString(),
            created = Date(),
            updated = Date(),
            expires = expires,
            ip = false,
            active = true,
            scope = scope
        )

        core.storageDriver.addPunishment(mute)
    }

    override fun unmute(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?, scope: String) {
        val activeMute = getActiveMute(vanguardPlayer, scope)
        if (activeMute != null) {
            activeMute.active = false
            activeMute.updated = Date()
            core.storageDriver.savePunishment(activeMute)
        }
    }

    override fun muteIp(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    ) {
        vanguardPlayer.knownIps.forEach {
            muteIp(it, vanguardPlayer.lastKnownName ?: core.messages.unknown, reason, source, duration, scope)
        }
    }

    override fun muteIp(
        address: String,
        targetName: String,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    ) {
        val expires: Date = if (duration != null) {
            Date.from(java.time.Instant.now().plus(duration))
        } else {
            Date(0)
        }

        val mute = Mute(
            id = UUID.randomUUID(),
            target = address,
            targetName = targetName,
            reason = reason,
            source = source.toString(),
            created = Date(),
            updated = Date(),
            expires = expires,
            ip = true,
            active = true,
            scope = scope
        )

        core.storageDriver.addPunishment(mute)
    }

    override fun unmuteIp(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?, scope: String) {
        vanguardPlayer.knownIps.forEach {
            unmuteIp(it, source, scope)
        }
    }

    override fun unmuteIp(address: String, source: VanguardOrigin?, scope: String) {
        val activeMute = getActiveMute(address, scope)
        if (activeMute != null) {
            activeMute.active = false
            activeMute.updated = Date()
            core.storageDriver.savePunishment(activeMute)
        }
    }
}