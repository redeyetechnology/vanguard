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

package ltd.redeye.vanguard.common.config

import org.slf4j.Logger
import org.spongepowered.configurate.ConfigurateException
import org.spongepowered.configurate.reactive.Subscriber
import java.io.Closeable
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.WatchEvent
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class ConfigManager(val dir: Path, val logger: Logger) : Closeable {

    companion object {
        private val configs: MutableMap<Class<*>, ConfigHandler<*>> = ConcurrentHashMap()
    }

    fun getFilePath(plugin: Path, fileName: String): Path {
        return Paths.get(plugin.toString() + File.separator + fileName)
    }

    override fun close() {
        for (configHandler in configs.values) {
            try {
                configHandler.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveConfig(config: Class<*>) {
        try {
            configs[config]!!.saveToFile()
        } catch (e: ConfigurateException) {
            e.printStackTrace()
        }
    }

    fun initConfigs(vararg configs: KClass<*>) = configs.forEach { initConfig(it) }

    fun initConfig(config: Class<*>) {
        logger.info("Initialising Configuration: {}", config.simpleName)
        val fileName = "${config.simpleName.lowercase().replace("config", "")}.yml"
        configs[config] = ConfigHandler(dir, fileName, config, logger)
    }

    fun initConfig(config: KClass<*>) {
        initConfig(config.java)
    }

    fun <T : Any> getConfig(config: KClass<T>): T = getConfig(config.java)

    fun <T> getConfig(config: Class<T>): T {
        return configs[config]!!.getConfig() as T
    }

    fun <T : Any> addListener(config: KClass<T>, listener: Subscriber<WatchEvent<*>>) {
        configs[config.java]!!.addListener(listener)
    }

    fun <T> addListener(config: Class<T>, listener: Subscriber<WatchEvent<*>>) {
        configs[config]!!.addListener(listener)
    }

}