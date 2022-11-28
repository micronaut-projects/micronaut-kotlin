/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.context.env.hocon

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigValue
import io.micronaut.context.env.ActiveEnvironment
import io.micronaut.context.env.EnvironmentPropertySource
import io.micronaut.context.env.PropertySource
import io.micronaut.context.env.PropertySourceLoader
import io.micronaut.core.io.ResourceLoader
import io.micronaut.core.order.Ordered
import io.micronaut.core.reflect.ClassUtils
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.LinkedHashMap

/**
 * A PropertySourceLoader that supports config4k.
 *
 * @author graemerocher
 * @author Marcelo Liberato
 * @since 1.1
 */
class HoconPropertySourceLoader : PropertySourceLoader, Ordered {

    private val impl by lazy {
        ClassUtils
                .forName("io.micronaut.context.env.hocon.HoconPropertySourceLoaderImpl", HoconPropertySourceLoader::class.java.classLoader)
                .get()
                .getDeclaredConstructor()
                .newInstance() as PropertySourceLoader
    }

    override fun getExtensions(): MutableSet<String> {
        return Collections.singleton("conf")
    }

    override fun isEnabled(): Boolean {
        return ClassUtils.isPresent("com.typesafe.config.Config", HoconPropertySourceLoader::class.java.classLoader)
    }

    override fun load(resourceName: String?, resourceLoader: ResourceLoader?): Optional<PropertySource> {
        return if(isEnabled) {
            impl.load(resourceName, resourceLoader)
        } else {
            Optional.empty()
        }
    }

    override fun read(name: String?, input: InputStream?): MutableMap<String, Any> {
        return if(isEnabled) {
            return impl.read(name, input)
        } else {
            Collections.emptyMap()
        }
    }

    override fun loadEnv(resourceName: String?, resourceLoader: ResourceLoader?, activeEnvironment: ActiveEnvironment?): Optional<PropertySource> {
        return if(isEnabled) {
            return impl.loadEnv(resourceName, resourceLoader, activeEnvironment)
        } else {
            Optional.empty()
        }
    }
}

class HoconPropertySourceLoaderImpl : PropertySourceLoader, Ordered {

    override fun load(resourceName: String?, resourceLoader: ResourceLoader?): Optional<PropertySource> {
        return loadEnv(resourceName, resourceLoader, null)
    }

    override fun loadEnv(resourceName: String?, resourceLoader: ResourceLoader?, activeEnvironment: ActiveEnvironment?): Optional<PropertySource> {
        if (resourceName != null) {
            if (activeEnvironment != null) {
                val environmentName = activeEnvironment.name
                val qualifiedName = "$resourceName-$environmentName"
                val resource = resourceLoader?.getResource("$qualifiedName.conf")
                if (resource != null && resource.isPresent) {
                    val config = resource.get().parseConfig()
                    return Optional.of(ConfigPropertySource(qualifiedName, config))
                }
            } else {
                val resource = resourceLoader?.getResource("$resourceName.conf")
                if (resource != null && resource.isPresent) {
                    val config = resource.get().parseConfig()
                    return Optional.of(ConfigPropertySource(resourceName, config))
                }
            }
        }
        return Optional.empty()
    }

    override fun read(name: String?, input: InputStream?): MutableMap<String, Any> {
        val config = ConfigFactory.parseReader(InputStreamReader(input, StandardCharsets.UTF_8))
        if (name != null) {
            val entrySet = config.entrySet()
            val map: MutableMap<String, Any> = LinkedHashMap()
            for (entry in entrySet) {
                val key = entry.key
                val value = entry.value
                map[key] = value.unwrapped()
            }
            return map
        }
        return Collections.emptyMap()
    }

    private fun URL.parseConfig(): Config =
            ConfigFactory
                    .parseURL(this)
                    .resolve()
}

class ConfigPropertySource(private val sourceName: String, private val config: Config) : PropertySource {

    override fun getOrder(): Int {
        return EnvironmentPropertySource.POSITION - 100
    }

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
