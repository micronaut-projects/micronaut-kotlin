package io.micronaut.ktor.env

import io.micronaut.context.env.Environment
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.function.Consumer

@MicronautTest
class MicronautKtorEnvironmentConfigTest {
    @Inject
    lateinit var env: Environment

    @Test
    fun testMap() {
        val config = MicronautKtorEnvironmentConfig(env = env)
        val msg = "Config key \"%s\" must resolve as an Environment property."
        val keys = config.keys()
        assertFalse(keys.isEmpty())
        keys.forEach(Consumer { s: String ->
            assertDoesNotThrow({
                config.property(s)
            }, String.format(msg, s))
        })
    }
}
