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

package ltd.redeye.vanguard.adapter

import ltd.redeye.vanguard.player.VanguardPlayer
import ltd.redeye.vanguard.player.VanguardPlayerAdapter
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class PaperVanguardPlayerManager : VanguardPlayerAdapter<Player> {
    companion object {
        val instance = PaperVanguardPlayerManager()
    }

    override fun adapt(player: Player): VanguardPlayer {
        TODO("Not yet implemented")
    }

    override fun adapt(player: VanguardPlayer): Player? {
        return Bukkit.getOfflinePlayer(player.uuid).player
    }
}