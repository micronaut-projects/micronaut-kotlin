package io.micronaut.ktor.env

import io.ktor.server.config.ApplicationConfigValue
import io.ktor.util.reflect.typeInfo
import io.micronaut.context.annotation.Property
import io.micronaut.context.env.Environment
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.function.Consumer

@Property(name = "ktor.test.scalar", value = "value")
@Property(name = "ktor.test.list[0]", value = "a")
@Property(name = "ktor.test.list[1]", value = "b")
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

    @Test
    fun testScalarAndListAccess() {
        val config = MicronautKtorEnvironmentConfig(env = env)
        val nestedConfig = config.config("ktor.test")

        assertEquals("value", nestedConfig.property("scalar").getString())
        assertEquals(listOf("a", "b"), nestedConfig.property("list").getList())
        assertNotNull(nestedConfig.propertyOrNull("scalar"))
    }

    @Test
    fun testApplicationConfigValueTypesAndAccessors() {
        val config = MicronautKtorEnvironmentConfig(env = env)
        val nestedConfig = config.config("ktor.test")

        val objectValue = config.property("ktor.test")
        val scalarValue = nestedConfig.property("scalar")
        val listValue = nestedConfig.property("list")

        assertEquals(ApplicationConfigValue.Type.OBJECT, objectValue.type)
        assertEquals(ApplicationConfigValue.Type.OBJECT, scalarValue.type)
        assertEquals(ApplicationConfigValue.Type.OBJECT, listValue.type)

        assertNotNull(objectValue.getMap())
        assertEquals("value", scalarValue.getString())
        assertEquals(listOf("a", "b"), listValue.getList())
    }
}
