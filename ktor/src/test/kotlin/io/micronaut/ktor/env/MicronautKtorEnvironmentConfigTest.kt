package io.micronaut.ktor.env

import io.ktor.server.config.ApplicationConfigurationException
import io.ktor.server.config.ApplicationConfigValue
import io.ktor.util.reflect.typeInfo
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Property
import io.micronaut.context.env.Environment
import io.micronaut.context.env.PropertySource
import io.micronaut.core.convert.ArgumentConversionContext
import io.micronaut.core.type.Argument
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.reflect.Proxy
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
        assertEquals("value", scalarValue.getAs(typeInfo<String>()))
        assertEquals(listOf("a", "b"), listValue.getList())
    }

    @Test
    fun testStandaloneValueTypesAndMissingConfigError() {
        ApplicationContext.builder()
            .propertySources(PropertySource.of("ktor-config-test", mapOf(
                "scalar" to "value",
                "list" to listOf("a", "b"),
            )))
            .start()
            .use { context ->
                val prefixedConfig = MicronautKtorEnvironmentConfig(env = context.environment, prefix = "missing")

                val exception = assertThrows(ApplicationConfigurationException::class.java) {
                    prefixedConfig.config("child")
                }
                assertEquals(
                    "No configuration found for path: child (resolved: missing.child)",
                    exception.message,
                )
            }
    }

    @Test
    fun testStandaloneValueTypeBranchesWithFakeEnvironment() {
        val fakeEnv = fakeEnvironment(
            scalarValues = mapOf("scalar" to "value"),
            listValues = mapOf("list" to listOf("a", "b")),
            mapValues = mapOf("scalar" to emptyMap()),
            directProperties = setOf("scalar", "list"),
            nestedProperties = setOf("object"),
        )

        val scalarValue = MicronautKtorEnvironmentConfig.KtorApplicationConfigValue("scalar", fakeEnv)
        val listValue = MicronautKtorEnvironmentConfig.KtorApplicationConfigValue("list", fakeEnv)

        assertEquals(ApplicationConfigValue.Type.SINGLE, scalarValue.type)
        assertEquals(ApplicationConfigValue.Type.LIST, listValue.type)
        assertEquals(emptyMap<String, Any?>(), scalarValue.getMap())
        assertEquals("value", scalarValue.getAs(typeInfo<String>()))
        assertEquals(listOf("a", "b"), listValue.getList())
    }

    private fun fakeEnvironment(
        scalarValues: Map<String, String>,
        listValues: Map<String, List<String>>,
        mapValues: Map<String, Map<String, Any?>>,
        directProperties: Set<String>,
        nestedProperties: Set<String>,
    ): Environment {
        return Proxy.newProxyInstance(
            Environment::class.java.classLoader,
            arrayOf(Environment::class.java),
        ) { _, method, args ->
            when (method.name) {
                "containsProperty" -> directProperties.contains(args[0] as String)
                "containsProperties" -> nestedProperties.contains(args[0] as String)
                "getProperty" -> resolveProperty(args, scalarValues, listValues, mapValues)
                "getRequiredProperty" -> scalarValues[args[0] as String]
                "getPropertySources" -> emptyList<PropertySource>()
                "getActiveNames" -> emptySet<String>()
                "getPropertyPathMatches" -> emptyList<List<String>>()
                "getPackages" -> emptyList<String>()
                "refreshAndDiff" -> emptyMap<String, Any?>()
                "getPropertySourceLoaders" -> emptyList<Any>()
                "getClassLoader" -> javaClass.classLoader
                "start", "stop", "addPropertySource", "removePropertySource", "addPackage",
                "addConfigurationExcludes", "addConfigurationIncludes" -> args?.firstOrNull() ?: Unit
                "isRunning" -> true
                "close" -> Unit
                else -> when (method.returnType) {
                    Boolean::class.javaPrimitiveType -> false
                    Boolean::class.javaObjectType -> false
                    Int::class.javaPrimitiveType -> 0
                    Int::class.javaObjectType -> 0
                    else -> null
                }
            }
        } as Environment
    }

    private fun resolveProperty(
        args: Array<Any?>?,
        scalarValues: Map<String, String>,
        listValues: Map<String, List<String>>,
        mapValues: Map<String, Map<String, Any?>>,
    ): java.util.Optional<*> {
        val name = args?.get(0) as String
        val targetType = when (val target = args[1]) {
            is ArgumentConversionContext<*> -> target.argument.type
            is Argument<*> -> target.type
            is Class<*> -> target
            else -> null
        }
        val value = when (targetType) {
            String::class.java -> scalarValues[name]
            List::class.java -> listValues[name]
            Map::class.java -> mapValues[name]
            else -> null
        }
        return java.util.Optional.ofNullable(value)
    }
}
