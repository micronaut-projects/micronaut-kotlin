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
package io.micronaut.ktor.factory

import io.ktor.routing.routing
import io.ktor.server.engine.*
import io.micronaut.context.annotation.Factory
import io.micronaut.context.env.Environment
import io.micronaut.core.annotation.Internal
import io.micronaut.core.io.socket.SocketUtils
import io.micronaut.http.server.HttpServerConfiguration
import io.micronaut.ktor.KtorApplication
import io.micronaut.ktor.KtorApplicationBuilder
import io.micronaut.ktor.KtorRoutingBuilder
import io.micronaut.ktor.env.MicronautKtorEnvironmentConfig
import jakarta.inject.Singleton

/**
 * The Ktor factory
 */
@Factory
@Internal
class KtorMicronautApplicationFactory {

    @Singleton
    fun applicationEngineEnvironmentBuilder(
            ktorApplication: KtorApplication<*>,
            ktorApplicationBuilders: List<KtorApplicationBuilder>,
            ktorRoutingBuilders: List<KtorRoutingBuilder>) : ApplicationEngineEnvironmentBuilder {
        ktorApplication.init()

        ktorApplicationBuilders.forEach {
            ktorApplication.environment.modules.add(it.builder)
        }

        ktorRoutingBuilders.forEach {
            ktorApplication.environment.modules.add {
                routing { it.builder(this) }
            }
        }

        return ktorApplication.environment
    }

    @EngineAPI
    @Singleton
    fun applicationEngineEnvironment(
            builder : ApplicationEngineEnvironmentBuilder,
            env : Environment,
            serverConfiguration: HttpServerConfiguration) : ApplicationEngineEnvironment {
        val connectors = builder.connectors
        if (connectors.isEmpty()) {
            var specifiedPort = serverConfiguration.port.orElse(8080)
            if (specifiedPort == -1) {
                specifiedPort = SocketUtils.findAvailableTcpPort()
            }
            builder.connector {
                host = serverConfiguration.host.orElse("0.0.0.0")
                port = specifiedPort
            }
        }
        return ApplicationEngineEnvironmentReloading(
                env.classLoader,
                builder.log,
                MicronautKtorEnvironmentConfig(env = env),
                connectors,
                builder.modules,
                builder.watchPaths
        )
    }
}

