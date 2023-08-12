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
import org.spongepowered.configurate.CommentedConfigurationNode
import org.spongepowered.configurate.ConfigurateException
import org.spongepowered.configurate.reactive.Subscriber
import org.spongepowered.configurate.reference.ConfigurationReference
import org.spongepowered.configurate.reference.ValueReference
import org.spongepowered.configurate.reference.WatchServiceListener
import org.spongepowered.configurate.yaml.NodeStyle
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.WatchEvent

class ConfigHandler<T>(applicationFolder: Path, configName: String, private val clazz: Class<T>, logger: Logger) :
    AutoCloseable {

    private val listener = WatchServiceListener.create()
    private lateinit var base: ConfigurationReference<CommentedConfigurationNode>
    private lateinit var config: ValueReference<T, CommentedConfigurationNode>

    private val configFile: Path = Paths.get(applicationFolder.toString() + File.separator + configName)

    init {

        try {
            base = this.listener.listenToConfiguration(
                { file ->
                    YamlConfigurationLoader.builder()
                        .nodeStyle(NodeStyle.BLOCK)
                        .defaultOptions {
                            it.shouldCopyDefaults(true)
                        }
                        .path(file)
                        .build()
                }, configFile
            )

            this.listener.listenToFile(configFile) { event ->
                logger.info("Updated ConfigFile ${configFile.fileName}")
            }

            this.config = this.base.referenceTo(clazz)
            this.base.save()

        } catch (throwable: Throwable) {
            logger.error("Error loading config file ${configFile.fileName}", throwable)
        }

    }

    fun getConfig(): T {
        return this.config.get()!!
    }

    @kotlin.jvm.Throws(ConfigurateException::class)
    fun saveToFile() {
        this.base.node().set(clazz, clazz.cast(getConfig()))
        this.base.loader().save(this.base.node())
    }

    fun addListener(listener: Subscriber<WatchEvent<*>>) {
        this.listener.listenToFile(this.configFile, listener)
    }

    override fun close() {
        this.listener.close()
        this.base.close()
    }


}