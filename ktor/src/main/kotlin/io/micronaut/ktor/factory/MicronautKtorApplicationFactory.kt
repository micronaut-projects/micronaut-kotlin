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

import io.ktor.server.application.ApplicationEnvironment
import io.ktor.server.application.ServerConfigBuilder
import io.ktor.server.routing.routing
import io.ktor.server.engine.*
import io.micronaut.context.annotation.Factory
import io.micronaut.context.env.Environment
import io.micronaut.core.io.socket.SocketUtils
import io.micronaut.http.server.HttpServerConfiguration
import io.micronaut.ktor.KtorApplication
import io.micronaut.ktor.KtorApplicationBuilder
import io.micronaut.ktor.KtorRoutingBuilder
import io.micronaut.ktor.env.MicronautKtorEnvironmentConfig
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import io.micronaut.core.annotation.Internal

/**
 * The Ktor factory
 */
@Factory
@Internal
class KtorMicronautApplicationFactory {

    private data class ConnectorInfo(
        val host: String,
        val port: Int
    )

    private var pendingConnector: ConnectorInfo? = null

    @Singleton
    fun applicationEngineEnvironmentBuilder(
        ktorApplication: KtorApplication<*>,
        ktorApplicationBuilders: List<KtorApplicationBuilder>,
        ktorRoutingBuilders: List<KtorRoutingBuilder>) : ServerConfigBuilder {
        ktorApplication.init()

        ktorApplicationBuilders.forEach {
            ktorApplication.environment.module(it.builder)
        }

        ktorRoutingBuilders.forEach {
            ktorApplication.environment.module {
                routing { it.builder(this) }
            }
        }

        return ktorApplication.environment
    }

    @Singleton
    fun applicationEngineEnvironment(
        builder : ServerConfigBuilder,
        env : Environment,
        serverConfiguration: HttpServerConfiguration) : ApplicationEnvironment {

        var specifiedPort = serverConfiguration.port.orElse(8080)
        if (specifiedPort == -1) {
            specifiedPort = SocketUtils.findAvailableTcpPort()
        }

        pendingConnector = ConnectorInfo(
            host = serverConfiguration.host.orElse("0.0.0.0"),
            port = specifiedPort
        )

        return applicationEnvironment {
            log = LoggerFactory.getLogger("ktor.application")
            config = MicronautKtorEnvironmentConfig(env = env)
        }
    }

    /**
     * Provides the engine configuration that includes the connector setup.
     */
    @Singleton
    fun <TConfiguration : ApplicationEngine.Configuration> getEngineConfiguration(
        ktorApplication: KtorApplication<TConfiguration>
    ): TConfiguration.() -> Unit {

        return {
            // Apply the pending connector if it exists
            if (connectors.isEmpty() && pendingConnector != null) {
                connector {
                    host = pendingConnector!!.host
                    port = pendingConnector!!.port
                }
            }

            // Apply custom configuration
            ktorApplication.configuration(this)
        }
    }
}
