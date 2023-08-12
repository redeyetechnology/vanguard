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

package ltd.redeye.vanguard.config

import org.spongepowered.configurate.loader.ConfigurationLoader
import org.spongepowered.configurate.objectmapping.ObjectMapper
import org.spongepowered.configurate.objectmapping.meta.NodeResolver
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.nio.file.Path

class ConfigManager {
    private fun createLoader(source: Path): ConfigurationLoader<*> {
        val factory = ObjectMapper.factoryBuilder()
            .addNodeResolver(NodeResolver.onlyWithSetting())
            .build()

        return YamlConfigurationLoader.builder()
            .path(source)
            .defaultOptions { options -> options.serializers { build -> build.registerAnnotatedObjects(factory) } }
            .build()
    }

    fun <T> loadConfig(source: Path, clazz: Class<T>, saveOnLoad: Boolean = true): T {
        val loader = createLoader(source)
        val loaded = loader.load()

        // Save the config to ensure all fields are present
        if (saveOnLoad) {
            loader.save(loaded)
        }

        return loaded.get(clazz)!!
    }

}