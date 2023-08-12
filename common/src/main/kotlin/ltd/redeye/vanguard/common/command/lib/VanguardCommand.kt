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

package ltd.redeye.vanguard.common.command.lib

import ltd.redeye.vanguard.common.command.lib.types.VanguardCommandSource
import ltd.redeye.vanguard.common.config.file.MessagesConfig
import ltd.redeye.vanguard.common.config.file.VanguardConfig
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.minimessage.MiniMessage

abstract class VanguardCommand {
    fun getMessages(): MessagesConfig {
        return ltd.redeye.vanguard.common.VanguardCore.instance.messages
    }

    fun getConfig(): VanguardConfig {
        return ltd.redeye.vanguard.common.VanguardCore.instance.config
    }

    fun message(target: VanguardCommandSource<*>, message: String, prefix: Boolean = true) {
        val messageComponent = MiniMessage.miniMessage().deserialize(message)
        val prefixComponent =
            if (prefix) MiniMessage.miniMessage().deserialize(getConfig().prefix)
            else Component.empty()

        target.sendMessage(Component.join(JoinConfiguration.noSeparators(), prefixComponent, messageComponent))
    }


}