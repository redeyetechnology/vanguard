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

package ltd.redeye.vanguard.common.command.lib.types

/**
 * Represents a source of a command that is a player.
 * @param PLAYER The type of player.
 * @see VanguardCommandSource - The base command source.
 */
interface VanguardPlayerCommandSource<Player>: VanguardCommandSource<Player> {

    /**
     * Gets the player associated with this source.
     * @return The player.
     */
    fun player(): Player

}