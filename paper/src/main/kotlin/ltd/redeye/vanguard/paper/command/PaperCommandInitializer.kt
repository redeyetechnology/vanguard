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

import cloud.commandframework.CommandManager
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator
import cloud.commandframework.paper.PaperCommandManager
import ltd.redeye.vanguard.common.command.lib.types.PlatformCommandInitializer
import ltd.redeye.vanguard.common.command.lib.types.VanguardCommandSource
import org.bukkit.plugin.java.JavaPlugin

class PaperCommandInitializer(private val plugin: JavaPlugin) : PlatformCommandInitializer {

    override fun createCommandManager(): CommandManager<VanguardCommandSource<*>> {

        val commandManager = PaperCommandManager(
            plugin,
            AsynchronousCommandExecutionCoordinator.builder<VanguardCommandSource<*>>().build(),
            { sender -> PaperCommandSource.from(sender) },
            { source -> (source as PaperCommandSource).sender }
        )

        try {
            commandManager.registerBrigadier()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return commandManager

    }
}