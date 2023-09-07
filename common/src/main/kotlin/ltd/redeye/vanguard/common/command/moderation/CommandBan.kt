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
import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.command.lib.VanguardCommand
import ltd.redeye.vanguard.common.command.lib.types.VanguardCommandSource
import ltd.redeye.vanguard.common.command.lib.types.VanguardPlayerCommandSource
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.VanguardPunishmentManager
import java.time.Duration

class CommandBan : VanguardCommand() {

    init {
        val punishmentManager = VanguardCore.instance.punishmentManager
    }

    @CommandMethod("ban <player> [reason]")
    @CommandPermission("vanguard.command.ban")
    @CommandDescription("Ban a player from the server.")
    fun ban(
        sender: VanguardCommandSource<*>,
        @Argument(
            "player",
            description = "The player to ban"
        ) target: VanguardPlayer,
        @Argument(
            "reason",
            description = "The reason why the player has been banned"
        ) @Greedy reason: String?,
        @Flag(
            "scope",
            permission = "vanguard.command.ban.scoped",
            description = "Which server to ban the player on"
        ) scope: String?,
    ) {
        val origin = if (sender.isConsole()) VanguardOrigin.CONSOLE else {
            val player = sender as VanguardPlayerCommandSource<*>
            VanguardOrigin(player.uuid(), player.name())
        }
        VanguardCore.instance.punishmentManager.ban(target, reason, origin, null, scope ?: VanguardPunishmentManager.GLOBAL_SCOPE)
    }

    @CommandMethod("tempban <player> <duration> [reason]")
    @CommandPermission("vanguard.command.tempban")
    @CommandDescription("Temporarily ban a player from the server.")
    fun banTemp(
        sender: VanguardCommandSource<*>,
        @Argument(
            "player",
            description = "The player to ban"
        ) target: VanguardPlayer,
        @Argument(
            "duration",
            description = "How long to ban the player for"
        ) duration: Duration,
        @Argument(
            "reason",
            description = "The reason why the player has been banned"
        ) @Greedy reason: String?,
        @Flag(
            "scope",
            description = "Which server to ban the player on"
        ) scope: String?
    ) {
        val origin = if (sender.isConsole()) VanguardOrigin.CONSOLE else {
            val player = sender as VanguardPlayerCommandSource<*>
            VanguardOrigin(player.uuid(), player.name())
        }
        VanguardCore.instance.punishmentManager.ban(target, reason, origin, duration, scope ?: VanguardPunishmentManager.GLOBAL_SCOPE)
    }

    @CommandMethod("banip <player> [reason]")
    @CommandPermission("vanguard.command.banip")
    @CommandDescription("Ban a player's IP from the server.")
    fun banIp(
        sender: VanguardCommandSource<*>,
        @Argument(
            "player",
            description = "The player to ban"
        ) target: VanguardPlayer,
        @Argument(
            "reason",
            description = "The reason why the player has been banned"
        ) @Greedy reason: String?,
        @Flag(
            "scope",
            description = "Which server to ban the player on"
        ) scope: String?
    ) {
        val origin = if (sender.isConsole()) VanguardOrigin.CONSOLE else {
            val player = sender as VanguardPlayerCommandSource<*>
            VanguardOrigin(player.uuid(), player.name())
        }
        VanguardCore.instance.punishmentManager.banIp(target, reason, origin, null, scope ?: VanguardPunishmentManager.GLOBAL_SCOPE)
    }

    @CommandMethod("tempbanip <player> <duration> [reason]")
    @CommandPermission("vanguard.command.tempbanip")
    @CommandDescription("Temporarily ban a player's IP from the server.")
    fun banIpTemp(
        sender: VanguardCommandSource<*>,
        @Argument(
            "player",
            description = "The player or IP to ban"
        ) target: VanguardPlayer,
        @Argument(
            "duration",
            description = "How long to ban the player for"
        ) duration: Duration,
        @Argument(
            "reason",
            description = "The reason why the player has been banned"
        ) @Greedy reason: String?,
        @Flag(
            "scope",
            description = "Which server to ban the player on"
        ) scope: String?
    ) {
        val origin = if (sender.isConsole()) VanguardOrigin.CONSOLE else {
            val player = sender as VanguardPlayerCommandSource<*>
            VanguardOrigin(player.uuid(), player.name())
        }
        VanguardCore.instance.punishmentManager.banIp(target, reason, origin, duration, scope ?: VanguardPunishmentManager.GLOBAL_SCOPE)
    }

    @CommandMethod("unban <player>")
    @CommandPermission("vanguard.command.unban")
    @CommandDescription("Unban a player from the server.")
    fun unban(
        sender: VanguardCommandSource<*>,
        @Argument(
            "player",
            description = "The player to unban"
        ) target: VanguardPlayer,
        @Flag(
            "scope",
            description = "Which server to ban the player on"
        ) scope: String?
    ) {
        val origin = if (sender.isConsole()) VanguardOrigin.CONSOLE else {
            val player = sender as VanguardPlayerCommandSource<*>
            VanguardOrigin(player.uuid(), player.name())
        }
        VanguardCore.instance.punishmentManager.unban(target, origin, scope ?: VanguardPunishmentManager.GLOBAL_SCOPE)
    }
}