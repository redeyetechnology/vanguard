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

import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.command.lib.types.PlatformCommandInitializer
import ltd.redeye.vanguard.common.network.messaging.proxy.MessagingProxy
import ltd.redeye.vanguard.common.player.VanguardPlayerAdapter
import ltd.redeye.vanguard.common.plugin.VanguardPlugin
import ltd.redeye.vanguard.paper.adapter.PaperVanguardPlayerAdapter
import ltd.redeye.vanguard.paper.command.PaperCommandInitializer
import ltd.redeye.vanguard.paper.listener.PlayerPaperEvents
import ltd.redeye.vanguard.paper.network.PaperSingleMessagingProxy
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.Logger
import java.io.File

class VanguardPlugin : JavaPlugin(), VanguardPlugin {

    lateinit var vanguard: VanguardCore

    override fun onEnable() {
        vanguard = VanguardCore(this)

        server.pluginManager.registerEvents(PlayerPaperEvents(), this)
    }

    override fun initMetrics() {
        Metrics(this, 19474)
    }

    override fun onDisable() {
    }

    override fun createVanguardPlayerAdapter(): VanguardPlayerAdapter<*> {
        return PaperVanguardPlayerAdapter()
    }

    override fun slf4jLogger(): Logger {
        return slF4JLogger
    }

    override fun version(): String {
        return pluginMeta.version
    }

    override fun dataFolder(): File {
        return dataFolder
    }

    override fun createPlatformCommandInitializer(): PlatformCommandInitializer {
        return PaperCommandInitializer(this)
    }

    override fun defaultMessagingProxy(): MessagingProxy {
        return PaperSingleMessagingProxy
    }

}