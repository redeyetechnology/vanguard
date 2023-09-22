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

package ltd.redeye.vanguard.common.command.moderation

import cloud.commandframework.annotations.*
import cloud.commandframework.annotations.specifier.Greedy
import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.command.lib.VanguardCommand
import ltd.redeye.vanguard.common.command.lib.types.VanguardCommandSource
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.VanguardPunishmentManager

class CommandWarn : VanguardCommand() {

    private val punishmentManager = VanguardCore.instance.punishmentManager

    @CommandMethod("warn <player> [reason]")
    @CommandPermission("vanguard.command.warn")
    @CommandDescription("Warn a player")
    fun mute(
        sender: VanguardCommandSource<*>,
        @Argument(
            "player",
            description = "The player to warn"
        ) target: VanguardPlayer,
        @Argument(
            "reason",
            description = "The reason why the player has been warned"
        ) @Greedy reason: String?,
        @Flag(
            "scope",
            permission = "vanguard.command.mute.scoped",
            description = "Which server to mute the player on"
        ) scope: String?,
    ) {
        val origin = getOrigin(sender)
        punishmentManager.warn(
            target,
            reason,
            origin,
            scope ?: VanguardPunishmentManager.GLOBAL_SCOPE
        )

        val core = VanguardCore.instance
        val resolver = buildGenericTagResolver(target, reason, origin, null)
        core.messagingProxy.alertStaff(core.messages.alerts.warned, resolver.build())
    }
}