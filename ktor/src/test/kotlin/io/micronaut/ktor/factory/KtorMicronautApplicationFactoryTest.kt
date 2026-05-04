package io.micronaut.ktor.factory

import io.ktor.server.engine.ApplicationEngine
import io.micronaut.context.ApplicationContext
import io.micronaut.http.server.HttpServerConfiguration
import io.micronaut.ktor.KtorApplication
import io.micronaut.ktor.KtorApplicationBuilder
import io.micronaut.ktor.KtorRoutingBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class KtorMicronautApplicationFactoryTest {

    @Test
    fun testFactoryBuildsEnvironmentModulesAndConnectorConfig() {
        ApplicationContext.builder().start().use { context ->
            val factory = KtorMicronautApplicationFactory()
            val ktorApplication = object : KtorApplication<ApplicationEngine.Configuration>({
                applicationEngineEnvironment {
                    log = LoggerFactory.getLogger("ktor-factory-test")
                }
            }) {}
            val environmentBuilder = factory.applicationEnvironmentBuilder(ktorApplication, context.environment)
            val modules = factory.applicationModules(
                ktorApplication,
                listOf(object : KtorApplicationBuilder({}) {}),
                listOf(object : KtorRoutingBuilder({}) {}),
            )
            val serverConfiguration = HttpServerConfiguration().apply {
                setContextPath("/factory")
                setPort(-1)
            }

            val serverConfig = factory.serverConfig(
                environmentBuilder,
                context.environment,
                serverConfiguration,
                modules,
            )
            val connector = factory.connectorConfigs(serverConfiguration).single()

            assertSame(context.environment.classLoader, environmentBuilder.classLoader)
            assertNotNull(environmentBuilder.config)
            assertNotNull(environmentBuilder.log)
            assertEquals(2, modules.size)
            assertNotNull(serverConfig)
            assertEquals("0.0.0.0", connector.host)
            assertTrue(connector.port > 0)
        }
    }
}
