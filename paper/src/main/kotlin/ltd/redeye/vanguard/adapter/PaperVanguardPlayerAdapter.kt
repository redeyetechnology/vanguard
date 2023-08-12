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
import net.kyori.adventure.audience.Audience
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class PaperVanguardPlayerAdapter : VanguardPlayerAdapter<Player> {
    companion object {
        val instance = PaperVanguardPlayerAdapter()
    }

    override fun adapt(player: Player): VanguardPlayer {
        TODO("Not yet implemented")
    }

    override fun adapt(player: VanguardPlayer): Player? {
        return Bukkit.getOfflinePlayer(player.uuid).player
    }

    override fun parse(input: String): VanguardPlayer? {
        TODO("Not yet implemented")
    }

    override fun audience(playerType: VanguardPlayer): Audience {
        return adapt(playerType)!!
    }

    override fun isOnline(vanguardPlayer: VanguardPlayer): Boolean {
        val player = adapt(vanguardPlayer)

        return player?.isOnline ?: false
    }
}