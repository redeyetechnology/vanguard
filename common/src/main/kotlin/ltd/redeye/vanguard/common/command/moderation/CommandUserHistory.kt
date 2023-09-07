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
import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.command.lib.VanguardCommand
import ltd.redeye.vanguard.common.command.lib.types.VanguardCommandSource
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.VanguardPunishmentManager
import ltd.redeye.vanguard.common.punishment.type.Ban
import ltd.redeye.vanguard.common.punishment.type.Kick
import ltd.redeye.vanguard.common.punishment.type.Mute
import ltd.redeye.vanguard.common.punishment.type.Warning
import ltd.redeye.vanguard.common.punishment.type.impl.ActivePunishment
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import java.text.SimpleDateFormat
import java.util.*

class CommandUserHistory : VanguardCommand() {

    @ProxiedBy("history")
    @CommandMethod("userhistory <player> [page]")
    @CommandDescription("View a player's punishment history.")
    @CommandPermission("vanguard.command.userhistory")
    fun userHistory(
        source: VanguardCommandSource<*>,
        @Argument("player", description = "The player to view the history of") target: VanguardPlayer,
        @Argument("page", description = "The page to view") page: Int?,
        @Flag("scope", description = "The server to view the history of") scope: String?,
    ) {
        val currentPage = page ?: 1

        var punishments = VanguardCore.instance.punishmentManager.getPunishments(target)
        val messageTemplate = VanguardCore.instance.messages.userHistory

        punishments = if (scope != null) {
            punishments.filter { it.scope == scope }
        } else {
            punishments.filter { it.scope == VanguardCore.instance.config.serverName || it.scope == VanguardPunishmentManager.GLOBAL_SCOPE }
        }

        if (punishments.isEmpty()) {
            message(source, "There are no punishments matching your criteria.", true)
            return
        }

        val pages = punishments.chunked(6)

        if (currentPage > pages.size) {
            message(source, "Invalid page number.", true)
            return
        }

        if (currentPage < 1) {
            message(source, "Invalid page number.", true)
            return
        }

        val miniMessage = MiniMessage.miniMessage()
        val message = ArrayList<Component>()

        val paddedName =
            (target.lastKnownName ?: "Unknown") + " ".repeat(16 - (target.lastKnownName ?: "Unknown").length)
        val headerTagResolver = TagResolver.builder()
            .tag("name_padded", Tag.selfClosingInserting(Component.text(paddedName)))
            .tag("current_page", Tag.selfClosingInserting(Component.text(currentPage)))
            .tag("max_page", Tag.selfClosingInserting(Component.text(pages.size)))
            .build()
        message.add(miniMessage.deserialize(messageTemplate.header, headerTagResolver))
        message.add(miniMessage.deserialize(messageTemplate.subHeading))

        val pageData = pages[currentPage - 1]
        pageData.forEach {
            val isActive = if (it is ActivePunishment) it.active else false
            val activePlaceholder =
                if (isActive) messageTemplate.activePlaceholder else messageTemplate.inactivePlaceholder

            // short reason is 12 chars and ... if longer
            val shortReason =
                if ((it.reason ?: "N/A").length > 12) (it.reason ?: "N/A").substring(0, 12) + "..." else it.reason
                    ?: "N/A"

            val duration = if (it is ActivePunishment) {
                if (it.expires == Date(0)) {
                    "Permanent"
                } else {
                    val end = it.expires
                    val start = it.created
                    val diff = end.time - start.time
                    val seconds = diff / 1000 % 60
                    val minutes = diff / (60 * 1000) % 60
                    val hours = diff / (60 * 60 * 1000) % 24
                    val days = diff / (24 * 60 * 60 * 1000)
                    "${days}d ${hours}h ${minutes}m ${seconds}s"
                }
            } else "N/A"

            val expires = if (it is ActivePunishment) {
                if (it.expires == Date(0)) {
                    "Never"
                } else {
                    // format expiry date/time as
                    // 01 Jan 2021 11:00PM GMT
                    val date = it.expires
                    SimpleDateFormat("dd MMM yyyy hh:mma z").format(date)
                }
            } else {
                "N/A"
            }

            val presentTense = VanguardCore.instance.messages.presentTense
            val presentAction = when (it) {
                is Ban -> presentTense.ban
                is Mute -> presentTense.mute
                is Warning -> presentTense.warn
                is Kick -> presentTense.kick
                else -> "Unknown"
            }

            val pastTense = VanguardCore.instance.messages.pastTense
            val pastAction = when (it) {
                is Ban -> pastTense.ban
                is Mute -> pastTense.mute
                is Warning -> pastTense.warn
                is Kick -> pastTense.kick
                else -> "Unknown"
            }

            val resolver = TagResolver.builder()
                .tag("active", Tag.selfClosingInserting(miniMessage.deserialize(activePlaceholder)))
                .tag("origin", Tag.selfClosingInserting(Component.text(it.source?.name ?: "Unknown")))
                .tag("short_reason", Tag.selfClosingInserting(Component.text(shortReason)))
                .tag("full_reason", Tag.selfClosingInserting(Component.text(it.reason ?: "No reason provided.")))
                .tag("duration", Tag.selfClosingInserting(Component.text(duration)))
                .tag("expires", Tag.selfClosingInserting(Component.text(expires)))
                .tag("action", Tag.selfClosingInserting(Component.text(presentAction)))
                .tag("action_plural", Tag.selfClosingInserting(Component.text(pastAction)))
                .tag("player", Tag.selfClosingInserting(Component.text(target.lastKnownName ?: "Unknown")))
                .build()

            message.add(miniMessage.deserialize(messageTemplate.actionRow, resolver))
        }

        val footerTagResolver = TagResolver.builder()
            .tag("prev", Tag.selfClosingInserting(Component.text(currentPage - 1)))
            .tag("next", Tag.selfClosingInserting(Component.text(currentPage + 1)))
            .build()

        message.add(miniMessage.deserialize(messageTemplate.footerRow, footerTagResolver))

        message.forEach { source.sendMessage(it) }
    }

}