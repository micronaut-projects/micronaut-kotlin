/*
 * Copyright 2017-2019 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.ktor.env

import io.ktor.config.ApplicationConfig
import io.ktor.config.ApplicationConfigValue
import io.ktor.config.ApplicationConfigurationException
import io.micronaut.context.env.Environment
import io.micronaut.core.type.Argument
import java.util.*

@io.ktor.util.KtorExperimentalAPI
class MicronautKotrEnvironmentConfig(val env : Environment, val prefix : String? = "") : ApplicationConfig {

    @io.ktor.util.KtorExperimentalAPI
    override fun config(path: String): ApplicationConfig {
        if (env.containsProperties(path)) {
            return MicronautKotrEnvironmentConfig(env, path)
        } else {
            throw ApplicationConfigurationException("No configuration found for path: $path")
        }
    }

    @io.ktor.util.KtorExperimentalAPI
    override fun configList(path: String): List<ApplicationConfig> {
        return Collections.singletonList(config(path))
    }

    @io.ktor.util.KtorExperimentalAPI
    override fun property(path: String): ApplicationConfigValue {
        val fullPath = if(prefix == null) path else "$prefix.$path"
        if (env.containsProperty(fullPath)) {
            return KotrApplicationConfigValue(fullPath, env)
        } else {
            throw ApplicationConfigurationException("No configuration found for path: $path")
        }
    }

    @io.ktor.util.KtorExperimentalAPI
    override fun propertyOrNull(path: String): ApplicationConfigValue? {
        val fullPath = "$prefix.$path"
        return if (env.containsProperty(fullPath)) {
            KotrApplicationConfigValue(fullPath, env)
        } else {
            null
        }
    }

    @Suppress("UNCHECKED_CAST")
    @io.ktor.util.KtorExperimentalAPI
    class KotrApplicationConfigValue(val prop : String, val env: Environment) : ApplicationConfigValue {
        override fun getList(): List<String> {
            val requiredProperty = env.getProperty(prop, Argument.of(List::class.java, String::class.java))
            return requiredProperty as List<String>
        }

        override fun getString(): String {
            return env.getRequiredProperty(prop, String::class.java)
        }
    }
}