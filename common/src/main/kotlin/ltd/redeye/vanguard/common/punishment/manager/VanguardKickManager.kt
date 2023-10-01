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
import ltd.redeye.vanguard.common.punishment.manager.type.KickManager
import ltd.redeye.vanguard.common.punishment.type.Kick
import net.kyori.adventure.text.Component
import java.util.*

class VanguardKickManager(private val core: VanguardCore) : KickManager {

    override fun kick(vanguardPlayer: VanguardPlayer, origin: VanguardOrigin, reason: String, message: Component, scope: String): Boolean {

        val kick = Kick(
            id = UUID.randomUUID(),
            target = vanguardPlayer.uuid.toString(),
            targetName = vanguardPlayer.knownNames.first(),
            reason = reason,
            source = origin,
            created = Date(),
            updated = Date(),
            scope = scope
        )

        core.storageDriver.addPunishment(kick)
        return core.messagingProxy.kickPlayer(vanguardPlayer.uuid, message, scope)
    }

    override fun kick(uuid: UUID, message: Component, scope: String): Boolean {
        return core.messagingProxy.kickPlayer(uuid, message, scope)
    }

}