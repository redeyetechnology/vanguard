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

package ltd.redeye.vanguard.common.punishment

import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.type.Ban
import ltd.redeye.vanguard.common.punishment.type.Punishment
import java.time.Duration
import java.util.Date
import java.util.UUID

class VanguardPunishmentManager(private val core: ltd.redeye.vanguard.common.VanguardCore) {
    fun getPunishments(vanguardPlayer: VanguardPlayer): List<Punishment> {
        val punishments = core.storageDriver.getPunishments(vanguardPlayer)
        return punishments.sortedBy { it.created }
    }

    fun getActiveBan(uniqueId: UUID): Ban? {
        return core.storageDriver.getActiveBan(uniqueId)
    }

    fun isBanned(vanguardPlayer: VanguardPlayer): Boolean {
        val activeBan = getActiveBan(vanguardPlayer.uuid)
        return activeBan != null
    }

    fun ban(vanguardPlayer: VanguardPlayer, reason: String?, source: VanguardOrigin, duration: Duration?) {

        val expires: Date = if (duration != null) {
            Date.from(java.time.Instant.now().plus(duration))
        } else {
            Date(0)
        }

        val ban = Ban(
            id = UUID.randomUUID(),
            target = vanguardPlayer.uuid.toString(),
            targetName = vanguardPlayer.knownNames.first(),
            reason = reason,
            source = source.toString(),
            created = Date(),
            updated = Date(),
            expires = expires,
            ip = false,
            active = true
        )

        core.storageDriver.addPunishment(ban)
    }
}