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
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.minimessage.MiniMessage

class CommandKick : VanguardCommand() {

    private val punishmentManager = VanguardCore.instance.punishmentManager

    @CommandMethod("kick <player> [reason]")
    @CommandPermission("vanguard.command.kick")
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


        val core = VanguardCore.instance
        val resolver = buildGenericTagResolver(target, reason ?: core.messages.noReasonProvided, origin, null)

        val kickScreen = core.messages.kickedScreen.map {
            MiniMessage.miniMessage().deserialize(it, resolver.build())
        }

        val combinedKickScreen = Component.join(JoinConfiguration.newlines(), kickScreen)

        punishmentManager.kick(
            target,
            origin,
            reason ?: core.messages.noReasonProvided,
            combinedKickScreen,
            scope ?: VanguardPunishmentManager.GLOBAL_SCOPE
        )
        core.messagingProxy.alertStaff(core.messages.alerts.kicked, resolver.build())
    }
}