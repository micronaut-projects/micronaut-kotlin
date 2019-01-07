package io.micronaut.context.env.hocon

import io.micronaut.context.env.DefaultEnvironment
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