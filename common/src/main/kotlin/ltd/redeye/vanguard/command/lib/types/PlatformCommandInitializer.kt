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

package ltd.redeye.vanguard.command.lib.types

import cloud.commandframework.CommandManager
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler

interface PlatformCommandInitializer {

    /**
     * Creates a command manager from the platform implementation
     * @return The command manager.
     */
    fun createCommandManager(): CommandManager<VanguardCommandSource<*>>

    /**
     * Creates an exception handler from the platform implementation. By default, all platforms use the same
     * exception handler, but this can be overridden if needed.
     * @param commandManager The command manager to apply the exception handler to.
     * @return The exception handler.
     */
    fun createExceptionHandler(commandManager: CommandManager<VanguardCommandSource<*>>): MinecraftExceptionHandler<VanguardCommandSource<*>> {
        return MinecraftExceptionHandler<VanguardCommandSource<*>>().apply {
            withInvalidSenderHandler()
            withNoPermissionHandler()
            withArgumentParsingHandler()
            withCommandExecutionHandler()
            apply(commandManager) { source -> source }
        }
    }

}