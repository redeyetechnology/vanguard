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