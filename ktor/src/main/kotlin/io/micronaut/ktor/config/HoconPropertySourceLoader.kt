package io.micronaut.ktor.config

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigValue
import io.micronaut.context.env.PropertySource
import io.micronaut.context.env.PropertySourceLoader
import io.micronaut.core.io.ResourceLoader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.LinkedHashMap

class HoconPropertySourceLoader : PropertySourceLoader {

    override fun getExtensions(): MutableSet<String> {
        return Collections.singleton("conf")
    }

    override fun read(name: String?, input: InputStream?): MutableMap<String, Any> {
        val config = ConfigFactory.parseReader(InputStreamReader(input, StandardCharsets.UTF_8))
        if (name != null) {
            val entrySet = config.entrySet()
            val map : MutableMap<String, Any> = LinkedHashMap()
            for (entry in entrySet) {
                val key = entry.key
                val value = entry.value
                map.put(key, value.unwrapped())
            }
        }
        return Collections.emptyMap()
    }

    override fun load(resourceName: String?, resourceLoader: ResourceLoader?, environmentName: String?): Optional<PropertySource> {
        if (resourceName != null) {
            if (environmentName != null) {
                val qualifiedName = "$resourceName-$environmentName"
                val resource = resourceLoader?.getResource("$qualifiedName.conf")
                if (resource != null && resource.isPresent()) {
                    val url = resource.get()
                    val config = ConfigFactory.parseURL(url)
                    return Optional.of(ConfigPropertySource(qualifiedName, config))
                }
            } else {
                val resource = resourceLoader?.getResource("$resourceName.conf")
                if (resource != null && resource.isPresent()) {
                    val url = resource.get()
                    val config = ConfigFactory.parseURL(url)
                    return Optional.of(ConfigPropertySource(resourceName, config))
                }
            }
        }
        return Optional.empty()
    }
}

class ConfigPropertySource(val sourceName: String, val config: Config) : PropertySource {
    override fun getName(): String {
        return sourceName
    }

    override fun iterator(): MutableIterator<String> {
        return Iterator(config.entrySet().iterator())
    }

    override fun get(key: String?): Any {
        return config.getAnyRef(key)
    }

    class Iterator(val iterator: MutableIterator<MutableMap.MutableEntry<String, ConfigValue>>) : MutableIterator<String> {
        override fun hasNext(): Boolean {
            return iterator.hasNext()
        }

        override fun next(): String {
            return iterator.next().key
        }

        override fun remove() {
            return iterator.remove()
        }

    }
}