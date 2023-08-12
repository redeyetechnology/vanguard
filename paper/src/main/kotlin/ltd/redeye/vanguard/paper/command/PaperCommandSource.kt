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

package ltd.redeye.vanguard.paper.command

import ltd.redeye.vanguard.common.command.lib.types.VanguardCommandSource
import net.kyori.adventure.audience.Audience
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

open class PaperCommandSource(val sender: CommandSender) : VanguardCommandSource<CommandSender> {

    override fun hasPermission(permission: String): Boolean {
        return this.sender.hasPermission(permission)
    }

    override fun isConsole(): Boolean {
        return this.sender is ConsoleCommandSender
    }

    override fun audience(): Audience {
        return this.sender
    }

    companion object {
        fun from(sender: CommandSender): PaperCommandSource {
            return if (sender is Player) {
                PaperPlayerCommandSource(sender)
            } else {
                PaperCommandSource(sender)
            }
        }
    }

}