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
import java.time.Duration

class CommandMute : VanguardCommand() {

    private val punishmentManager = VanguardCore.instance.punishmentManager

    @CommandMethod("mute <player> [reason]")
    @CommandPermission("vanguard.command.mute")
    @CommandDescription("mute a player from the server.")
    fun mute(
        sender: VanguardCommandSource<*>,
        @Argument(
            "player",
            description = "The player to mute"
        ) target: VanguardPlayer,
        @Argument(
            "reason",
            description = "The reason why the player has been mutened"
        ) @Greedy reason: String?,
        @Flag(
            "scope",
            permission = "vanguard.command.mute.scoped",
            description = "Which server to mute the player on"
        ) scope: String?,
    ) {
        val origin = getOrigin(sender)
        punishmentManager.mute(
            target,
            reason,
            origin,
            null,
            scope ?: VanguardPunishmentManager.GLOBAL_SCOPE
        )

        val core = VanguardCore.instance
        val resolver = buildGenericTagResolver(target, reason, origin, null)
        core.messagingProxy.alertStaff(core.messages.alerts.permanentlyMuted, resolver.build())
    }

    @CommandMethod("tempmute <player> <duration> [reason]")
    @CommandPermission("vanguard.command.tempmute")
    @CommandDescription("Temporarily mute a player from the server.")
    fun muteTemp(
        sender: VanguardCommandSource<*>,
        @Argument(
            "player",
            description = "The player to mute"
        ) target: VanguardPlayer,
        @Argument(
            "duration",
            description = "How long to mute the player for"
        ) duration: Duration,
        @Argument(
            "reason",
            description = "The reason why the player has been mutened"
        ) @Greedy reason: String?,
        @Flag(
            "scope",
            description = "Which server to mute the player on"
        ) scope: String?
    ) {
        val origin = getOrigin(sender)
        val mute = punishmentManager.mute(
            target,
            reason,
            origin,
            duration,
            scope ?: VanguardPunishmentManager.GLOBAL_SCOPE
        )

        val core = VanguardCore.instance
        val resolver = buildGenericTagResolver(target, reason, origin, mute.getFormattedDuration())
        core.messagingProxy.alertStaff(core.messages.alerts.permanentlyMuted, resolver.build())
    }

    @CommandMethod("muteip <player> [reason]")
    @CommandPermission("vanguard.command.muteip")
    @CommandDescription("mute a player's IP from the server.")
    fun muteIp(
        sender: VanguardCommandSource<*>,
        @Argument(
            "player",
            description = "The player to mute"
        ) target: VanguardPlayer,
        @Argument(
            "reason",
            description = "The reason why the player has been mutened"
        ) @Greedy reason: String?,
        @Flag(
            "scope",
            description = "Which server to mute the player on"
        ) scope: String?
    ) {
        val origin = getOrigin(sender)
        val mute = punishmentManager.muteIp(
            target,
            reason,
            origin,
            null,
            scope ?: VanguardPunishmentManager.GLOBAL_SCOPE
        )

        val core = VanguardCore.instance
        val resolver = buildGenericTagResolver(target, reason, origin, null)
        core.messagingProxy.alertStaff(core.messages.alerts.permanentlyIpMuted, resolver.build())
    }

    @CommandMethod("tempmuteip <player> <duration> [reason]")
    @CommandPermission("vanguard.command.tempmuteip")
    @CommandDescription("Temporarily mute a player's IP from the server.")
    fun muteIpTemp(
        sender: VanguardCommandSource<*>,
        @Argument(
            "player",
            description = "The player or IP to mute"
        ) target: VanguardPlayer,
        @Argument(
            "duration",
            description = "How long to mute the player for"
        ) duration: Duration,
        @Argument(
            "reason",
            description = "The reason why the player has been mutened"
        ) @Greedy reason: String?,
        @Flag(
            "scope",
            description = "Which server to mute the player on"
        ) scope: String?
    ) {
        val origin = getOrigin(sender)
        val mute = punishmentManager.muteIp(
            target,
            reason,
            origin,
            duration,
            scope ?: VanguardPunishmentManager.GLOBAL_SCOPE
        )

        val core = VanguardCore.instance
        val resolver = buildGenericTagResolver(target, reason, origin, mute.first().getFormattedDuration())
        core.messagingProxy.alertStaff(core.messages.alerts.temporarilyIpMuted, resolver.build())
    }

    @CommandMethod("unmute <player>")
    @CommandPermission("vanguard.command.unmute")
    @CommandDescription("Unmute a player from the server.")
    fun unmute(
        sender: VanguardCommandSource<*>,
        @Argument(
            "player",
            description = "The player to unmute"
        ) target: VanguardPlayer,
        @Flag(
            "scope",
            description = "Which server to mute the player on"
        ) scope: String?
    ) {
        val origin = getOrigin(sender);
        punishmentManager.unmute(target, origin, scope ?: VanguardPunishmentManager.GLOBAL_SCOPE)

        val core = VanguardCore.instance
        val resolver = buildGenericTagResolver(target, null, origin, null)
        core.messagingProxy.alertStaff(core.messages.alerts.unmuted, resolver.build())
    }
}