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
import java.io.File
import java.nio.file.Path

/**
 * The VanguardCore is a shared class which contains all the core functionality of Vanguard. It's constructed by each
 * platform-specific implementation of Vanguard.
 */
class VanguardCore(pluginDirectory: File, val playerAdapter: VanguardPlayerAdapter<*>) {
    val punishmentManager = VanguardPunishmentManager(this)
    val playerManager = VanguardPlayerManager(this)
    val config: VanguardConfig
    val messages: MessagesConfig
    val storageDriver: VanguardStorageDriver

    companion object {
        lateinit var instance: ltd.redeye.vanguard.common.VanguardCore
    }

    init {
        ltd.redeye.vanguard.common.VanguardCore.Companion.instance = this

        val configManager = ConfigManager()

        config = configManager.loadConfig(
            Path.of(pluginDirectory.path.toString(), "config.yml"),
            VanguardConfig::class.java
        )

        messages = configManager.loadConfig(
            Path.of(pluginDirectory.path.toString(), "messages.yml"),
            MessagesConfig::class.java
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