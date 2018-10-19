package io.micronaut.ktor.env

import io.ktor.application.ApplicationEnvironment
import io.ktor.application.ApplicationEvents
import io.ktor.config.ApplicationConfig
import io.micronaut.context.env.Environment
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class MicronautKotrEnviroment(
        val micronautEnv: Environment) : ApplicationEnvironment {
    override val log: Logger
        get() = LoggerFactory.getLogger("ktor.application")

    override val classLoader: ClassLoader
        get() = micronautEnv.classLoader
    @io.ktor.util.KtorExperimentalAPI
    override val config: ApplicationConfig
        get() = MicronautKotrEnvironmentConfig(micronautEnv)
    override val monitor: ApplicationEvents
        get() = ApplicationEvents()
}