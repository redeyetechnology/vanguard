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
import ltd.redeye.vanguard.common.command.lib.types.PlatformCommandInitializer
import ltd.redeye.vanguard.common.player.VanguardPlayerAdapter
import ltd.redeye.vanguard.common.config.ConfigManager
import ltd.redeye.vanguard.common.config.file.MessagesConfig
import ltd.redeye.vanguard.common.config.file.VanguardConfig
import ltd.redeye.vanguard.common.player.VanguardPlayerManager
import ltd.redeye.vanguard.common.punishment.VanguardPunishmentManager
import ltd.redeye.vanguard.common.storage.VanguardStorageDriver
import ltd.redeye.vanguard.common.storage.mongo.MongoStorageDriver
import org.slf4j.Logger
import java.io.File

/**
 * The VanguardCore is a shared class which contains all the core functionality of Vanguard. It's constructed by each
 * platform-specific implementation of Vanguard.
 */
class VanguardCore(
    val playerAdapter: VanguardPlayerAdapter<*>,
    val logger: Logger,
    val version: String,
    pluginDirectory: File,
    commandInitializer: PlatformCommandInitializer
) {
    companion object {
        lateinit var instance: VanguardCore
    }

    init {
        instance = this
    }

    // Configurations
    private val configManager: ConfigManager = ConfigManager(pluginDirectory.toPath(), logger).apply {
        if (!pluginDirectory.exists()) {
            pluginDirectory.mkdirs()
        }
        initConfigs(VanguardConfig::class, MessagesConfig::class)
    }

    val config: VanguardConfig get() = configManager.getConfig(VanguardConfig::class)
    val messages: MessagesConfig get() = configManager.getConfig(MessagesConfig::class)

    val punishmentManager = VanguardPunishmentManager(this)
    val playerManager = VanguardPlayerManager(this)
    val commandManager = VanguardCommandManager(commandInitializer)
    val storageDriver: VanguardStorageDriver = when (config.database.driver) {
        VanguardStorageDriver.DriverType.MONGO -> MongoStorageDriver().apply { initialise() }
    }
    val api = VanguardApiImpl(this)
}