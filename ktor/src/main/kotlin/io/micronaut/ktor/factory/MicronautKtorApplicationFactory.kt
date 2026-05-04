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

import io.ktor.server.application.Application
import io.ktor.server.application.ServerConfig
import io.ktor.server.application.serverConfig
import io.ktor.server.engine.ApplicationEnvironmentBuilder
import io.ktor.server.engine.EngineConnectorConfig
import io.ktor.server.engine.applicationEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.routing.routing
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
    fun applicationEnvironmentBuilder(
        ktorApplication: KtorApplication<*>,
        env: Environment,
    ): ApplicationEnvironmentBuilder {
        ktorApplication.init()
        return ApplicationEnvironmentBuilder().apply {
            classLoader = env.classLoader
            config = MicronautKtorEnvironmentConfig(env = env)
            ktorApplication.environment(this)
        }
    }

    @Singleton
    fun applicationModules(
        ktorApplication: KtorApplication<*>,
        ktorApplicationBuilders: List<KtorApplicationBuilder>,
        ktorRoutingBuilders: List<KtorRoutingBuilder>,
    ): List<Application.() -> Unit> {
        val modules = mutableListOf<Application.() -> Unit>()
        ktorApplicationBuilders.forEach { modules.add(it.builder) }
        ktorRoutingBuilders.forEach { routingBuilder ->
            modules.add {
                routing { routingBuilder.builder(this) }
            }
        }
        return modules
    }

    @Singleton
    fun serverConfig(
        environmentBuilder: ApplicationEnvironmentBuilder,
        env: Environment,
        serverConfiguration: HttpServerConfiguration,
        applicationModules: List<Application.() -> Unit>,
    ): ServerConfig {
        val environment = applicationEnvironment {
            classLoader = environmentBuilder.classLoader
            log = environmentBuilder.log
            config = environmentBuilder.config
        }
        return serverConfig(environment) {
            developmentMode = env.activeNames.contains(Environment.DEVELOPMENT)
            rootPath = serverConfiguration.contextPath ?: ""
            applicationModules.forEach { module(it) }
        }
    }

    @Singleton
    fun connectorConfigs(serverConfiguration: HttpServerConfiguration): List<EngineConnectorConfig> {
        val connectorBuilder = ApplicationEngineConnectorBuilderHolder()
        val specifiedPort = resolvePort(serverConfiguration)
        connectorBuilder.connector {
            host = serverConfiguration.host.orElse("0.0.0.0")
            port = specifiedPort
        }
        return connectorBuilder.connectors
    }

    private fun resolvePort(serverConfiguration: HttpServerConfiguration): Int {
        val configuredPort = serverConfiguration.port.orElse(8080)
        return if (configuredPort == -1) SocketUtils.findAvailableTcpPort() else configuredPort
    }

    private class ApplicationEngineConnectorBuilderHolder : io.ktor.server.engine.ApplicationEngine.Configuration()
}
