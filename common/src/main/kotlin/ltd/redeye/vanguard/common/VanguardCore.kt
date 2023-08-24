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

package ltd.redeye.vanguard.common

import ltd.redeye.vanguard.common.api.VanguardApiImpl
import ltd.redeye.vanguard.common.command.lib.VanguardCommandManager
import ltd.redeye.vanguard.common.config.ConfigManager
import ltd.redeye.vanguard.common.config.file.MessagesConfig
import ltd.redeye.vanguard.common.config.file.VanguardConfig
import ltd.redeye.vanguard.common.network.messaging.proxy.MessagingProxy
import ltd.redeye.vanguard.common.network.messaging.proxy.RedisMessagingProxy
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.player.VanguardPlayerManager
import ltd.redeye.vanguard.common.plugin.VanguardPlugin
import ltd.redeye.vanguard.common.punishment.VanguardPunishmentManager
import ltd.redeye.vanguard.common.storage.VanguardStorageDriver
import ltd.redeye.vanguard.common.storage.mongo.MongoStorageDriver
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * The VanguardCore is a shared class which contains all the core functionality of Vanguard. It's constructed by each
 * platform-specific implementation of Vanguard.
 */
class VanguardCore(private val vanguardPlugin: VanguardPlugin) {

    companion object {
        lateinit var instance: VanguardCore
    }

    init {
        instance = this
    }

    val playerAdapter = vanguardPlugin.createVanguardPlayerAdapter()
    val logger = vanguardPlugin.slf4jLogger()
    val version = vanguardPlugin.version()

    // Configurations
    private val configManager: ConfigManager = ConfigManager(vanguardPlugin.dataFolder().toPath(), logger).apply {
        if (!vanguardPlugin.dataFolder().exists()) {
            vanguardPlugin.dataFolder().mkdirs()
        }
        initConfigs(VanguardConfig::class, MessagesConfig::class)
    }

    val config: VanguardConfig get() = configManager.getConfig(VanguardConfig::class)
    val messages: MessagesConfig get() = configManager.getConfig(MessagesConfig::class)

    val punishmentManager = VanguardPunishmentManager(this)
    val playerManager = VanguardPlayerManager(this)
    val commandManager = VanguardCommandManager(vanguardPlugin.createPlatformCommandInitializer())
    val storageDriver: VanguardStorageDriver = when (config.database.driver) {
        VanguardStorageDriver.DriverType.MONGO -> MongoStorageDriver().apply { initialise() }
    }
    val api = VanguardApiImpl(this)

    val messagingProxy = selectMessagingProxy()

    init {
        if (config.sendAnonymousStats) {
            vanguardPlugin.initMetrics()
        }
    }

    private fun selectMessagingProxy(): MessagingProxy {
        return if (config.network.enabled) {
            RedisMessagingProxy(vanguardPlugin.defaultMessagingProxy())
        } else {
            vanguardPlugin.defaultMessagingProxy()
        }
    }

    fun getVanguardPlayer(uuid: UUID): CompletableFuture<VanguardPlayer> {
        return CompletableFuture.supplyAsync { this.storageDriver.loadPlayer(uuid) }
    }

    fun getVanguardPlayer(name: String): CompletableFuture<VanguardPlayer?> {
        return CompletableFuture.supplyAsync { this.storageDriver.loadPlayer(name) }
    }

}