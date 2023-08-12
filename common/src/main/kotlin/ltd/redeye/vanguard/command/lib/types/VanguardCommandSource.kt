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

import net.kyori.adventure.audience.ForwardingAudience

/**
 * Represents a source of a command.
 * This can be a player or the console
 */
interface VanguardCommandSource<Source>: ForwardingAudience.Single {

    /**
     * Checks if the source has the given permission.
     * @param permission The permission to check.
     * @return `true` if the source has the permission, `false` otherwise.
     */
    fun hasPermission(permission: String): Boolean

    /**
     * Checks if the source is the console.
     * @return `true` if the source is the console, `false` otherwise.
     */
    fun isConsole(): Boolean

}