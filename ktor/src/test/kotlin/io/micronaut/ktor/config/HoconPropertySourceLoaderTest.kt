package io.micronaut.ktor.config

import io.micronaut.context.env.DefaultEnvironment
import io.micronaut.context.env.Environment
import org.junit.jupiter.api.Test

class HoconPropertySourceLoaderTest {


    @Test
    fun testPropertySourceLoader() {
        val env = DefaultEnvironment()
        env.start()

        val value = env.getProperty("micronaut.server.port", Integer::class.java)
        assert(
                value.get().toInt() == 8081
        )
    }
}