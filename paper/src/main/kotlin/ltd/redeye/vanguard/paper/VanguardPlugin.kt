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

package ltd.redeye.vanguard.paper

import ltd.redeye.vanguard.paper.adapter.PaperVanguardPlayerAdapter
import ltd.redeye.vanguard.paper.listener.PlayerPaperEvents
import org.bukkit.plugin.java.JavaPlugin

class VanguardPlugin : JavaPlugin() {

    lateinit var vanguard: ltd.redeye.vanguard.common.VanguardCore

    override fun onEnable() {
        vanguard = ltd.redeye.vanguard.common.VanguardCore(dataFolder, PaperVanguardPlayerAdapter())

        server.pluginManager.registerEvents(PlayerPaperEvents(), this)
    }

    override fun onDisable() {
    }

}