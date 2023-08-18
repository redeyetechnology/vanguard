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

package ltd.redeye.vanguard.common.config.file

import ltd.redeye.vanguard.common.config.file.messages.AlertsMessages
import ltd.redeye.vanguard.common.config.file.messages.ExpiryPlaceholders
import ltd.redeye.vanguard.common.message.VanguardMessage
import ltd.redeye.vanguard.common.message.section.MessageSound
import ltd.redeye.vanguard.common.message.section.MessageTitle
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
data class MessagesConfig(
    @Comment("This is an example message, it demonstrates how each message in this configuration file is formatted. Each section ('chat', 'actionbar', etc) is optional, and can be entirely omitted. Each text component uses the MiniMessage messaging format to format text. You can see this message in-game by running /vanguard showexamplemessage")
    var exampleMessage: VanguardMessage = VanguardMessage(
        chat = mutableListOf("<red>Message One", "<#00ff00><bold>Message Two", "<gradient:red:blue>Message Three"),
        "Example Actionbar",
        MessageTitle("Example Title", "Example Subtitle", 10, 10, 10),
        MessageSound("minecraft:entity.experience_orb.pickup", 1.0F, 1.0F)
    ),

    @Comment("This formats how the <expiry> placeholder is displayed. You can use the following placeholders: <date>, <countdown>. If a permanent ban is set, then the <expiry> placeholder will be replaced with the 'permanent' value.")
    var expiryPlaceholders: ExpiryPlaceholders = ExpiryPlaceholders(),

    @Comment("Messages shown to server operators when a Vanguard event occurs.")
    var alerts: AlertsMessages = AlertsMessages(),

    @Comment("This formats the <reason> if no reason is provided when banning, kicking, warning or muting a player.")
    var noReasonProvided: String = "No reason provided",

    @Comment("When a name is unknown for any reason, it is displayed as below.")
    var unknown: String = "Unknown",

    @Comment("This is the message displayed when a player attempts to login while banned. Placeholders: <reason>, <expiry>")
    var disallowedLoginBanned: List<String> = mutableListOf("<red>You are unable to login because you are banned.", "", "<gray>Reason: <white><reason>", "<gray>Expires: <white><expiry>"),
)