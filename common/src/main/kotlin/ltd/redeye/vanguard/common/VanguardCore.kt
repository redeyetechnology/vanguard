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
class VanguardCore(pluginDirectory: File, val playerAdapter: VanguardPlayerAdapter<*>, logger: Logger) {
    val punishmentManager = VanguardPunishmentManager(this)
    val playerManager = VanguardPlayerManager(this)
    val configManager: ConfigManager

    val config: VanguardConfig
        get() { return configManager.getConfig(VanguardConfig::class) }
    val messages: MessagesConfig
        get() { return configManager.getConfig(MessagesConfig::class) }

    val storageDriver: VanguardStorageDriver

    companion object {
        lateinit var instance: VanguardCore
    }

    init {
        instance = this

        if(!pluginDirectory.exists()) {
            pluginDirectory.mkdirs()
        }

        configManager = ConfigManager(pluginDirectory.toPath(), logger)
        configManager.initConfigs(
            VanguardConfig::class,
            MessagesConfig::class
        )
    }

    init {
        if (config.database.driver == VanguardStorageDriver.DriverType.MONGO) {
            storageDriver = MongoStorageDriver()
        } else {
            throw RuntimeException("Unsupported database driver: ${config.database.driver}")
        }

        storageDriver.initialise()
    }
}