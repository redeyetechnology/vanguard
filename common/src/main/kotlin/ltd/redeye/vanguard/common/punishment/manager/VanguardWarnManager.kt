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
import ltd.redeye.vanguard.common.punishment.manager.type.WarnManager
import ltd.redeye.vanguard.common.punishment.type.Warning
import java.util.*

class VanguardWarnManager(private val core: VanguardCore) : WarnManager {

    override fun warn(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin,
        scope: String
    ) {

        val warning = Warning(
            id = UUID.randomUUID(),
            target = vanguardPlayer.uuid.toString(),
            targetName = vanguardPlayer.knownNames.first(),
            reason = reason,
            source = source,
            created = Date(),
            updated = Date(),
            scope = scope
        )

        core.storageDriver.addPunishment(warning)
    }

    override fun getWarns(vanguardPlayer: VanguardPlayer, scope: String): Set<Warning> {
        return core.storageDriver.getWarns(vanguardPlayer, scope)
    }

    override fun getWarns(uuid: UUID, scope: String): Set<Warning> {
        return core.storageDriver.getWarns(uuid, scope)
    }

}