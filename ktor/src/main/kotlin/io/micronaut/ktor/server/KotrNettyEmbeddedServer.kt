package io.micronaut.ktor.server

import io.ktor.server.engine.ApplicationEngineEnvironment
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.http.server.HttpServerConfiguration
import io.micronaut.ktor.KotrApplication
import javax.inject.Singleton

@Singleton
@Requires(classes = arrayOf(Netty::class))
class KotrNettyEmbeddedServer(
        override val ctx: ApplicationContext,
        override val serverConfiguration: HttpServerConfiguration,
        override val engineEnvironment: ApplicationEngineEnvironment,
        val kotrApplication: KotrApplication<NettyApplicationEngine.Configuration>) : AbstractKotrEmbeddedServer(
        ctx,
        serverConfiguration,
        engineEnvironment,
        Netty.create(engineEnvironment, kotrApplication.configuration)
)