package io.micronaut.ktor.server

import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineEnvironment
import io.ktor.server.jetty.Jetty
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.http.server.HttpServerConfiguration
import io.micronaut.ktor.KotrApplication
import javax.inject.Singleton

@Singleton
@Requires(classes = arrayOf(Jetty::class))
class KotrJettyEmbeddedServer(
        override val ctx: ApplicationContext,
        override val serverConfiguration: HttpServerConfiguration,
        override val engineEnvironment: ApplicationEngineEnvironment,
        val kotrApplication: KotrApplication<ApplicationEngine.Configuration>) : AbstractKotrEmbeddedServer(
        ctx,
        serverConfiguration,
        engineEnvironment,
        Jetty.create(engineEnvironment, kotrApplication.configuration)
)