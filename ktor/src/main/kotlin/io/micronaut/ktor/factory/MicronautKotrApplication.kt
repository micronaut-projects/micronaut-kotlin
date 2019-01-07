package io.micronaut.ktor.factory

import io.ktor.server.engine.ApplicationEngineEnvironment
import io.ktor.server.engine.ApplicationEngineEnvironmentBuilder
import io.ktor.server.engine.ApplicationEngineEnvironmentReloading
import io.ktor.server.engine.connector
import io.ktor.util.KtorExperimentalAPI
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.env.Environment
import io.micronaut.core.io.socket.SocketUtils
import io.micronaut.http.server.HttpServerConfiguration
import io.micronaut.ktor.KotrApplication
import io.micronaut.ktor.env.MicronautKotrEnvironmentConfig
import javax.inject.Singleton

@Factory
class KotrMicronautApplicationFactory {



    @Singleton
    @Bean
    fun applicationEngineEnvironmentBuilder(kotrApplication: KotrApplication<*>) : ApplicationEngineEnvironmentBuilder {
        return kotrApplication.environment
    }

    @Singleton
    @Bean
    @KtorExperimentalAPI
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
                MicronautKotrEnvironmentConfig(env = env),
                connectors,
                builder.modules,
                builder.watchPaths
        )
    }
}

