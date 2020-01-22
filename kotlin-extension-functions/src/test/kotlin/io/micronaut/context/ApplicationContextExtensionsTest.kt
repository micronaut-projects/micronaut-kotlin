package io.micronaut.context

import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Prototype
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.MapPropertySource
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

/**
 * @author Alejandro Gomez
 */
class ApplicationContextTest {

    @Test
    fun run() {
        val context = run<TestFactory>()
        assertNotNull(context)
    }

    @Test
    fun runWithEnvironment() {
        val context = run<TestFactory>("baz")
        assertNotNull(context)
    }

    @Test
    fun runWithPropertiesAndEnvironment() {
        val context = run<TestFactory>(mapOf("bar.enabled" to "true"), "baz")
        assertNotNull(context)
    }

    @Test
    fun runWithPropertySourceAndEnvironment() {
        val context = run<TestFactory>(
                MapPropertySource(
                        "bar",
                        mapOf("bar.enabled" to "true")
                ), "baz"
        )
        assertNotNull(context)
    }

    @Test
    fun buildAndStart() {
        val context = buildAndStart<TestFactory>()
        assertNotNull(context.getBean(TestFactory.Foo::class.java))
        assertFalse(context.findBean(TestFactory.Bar::class.java).isPresent)
        assertFalse(context.findBean(TestFactory.Baz::class.java).isPresent)
    }

    @Test
    fun buildAndStartWithProperties() {
        val context =
                buildAndStart<TestFactory>(mapOf("bar.enabled" to "true"))
        assertNotNull(context.getBean(TestFactory.Foo::class.java))
        assertNotNull(context.getBean(TestFactory.Bar::class.java))
        assertFalse(context.findBean(TestFactory.Baz::class.java).isPresent)
    }

    @Test
    fun buildAndStartWithPropertySource() {
        val context = buildAndStart<TestFactory>(
                MapPropertySource("foo", mapOf("foo.enabled" to "true")),
                MapPropertySource("bar", mapOf("bar.enabled" to "true"))
        )
        assertNotNull(context.getBean(TestFactory.Foo::class.java))
        assertNotNull(context.getBean(TestFactory.Bar::class.java))
        assertFalse(context.findBean(TestFactory.Baz::class.java).isPresent)
    }

    @Test
    fun buildAndStartWithEnvironment() {
        val context = buildAndStart<TestFactory>("baz")
        assertNotNull(context.getBean(TestFactory.Foo::class.java))
        assertFalse(context.findBean(TestFactory.Bar::class.java).isPresent)
        assertNotNull(context.getBean(TestFactory.Baz::class.java))
    }

    @Test
    fun buildAndStartWithPropertiesAndEnvironment() {
        val context =
                buildAndStart<TestFactory>(mapOf("bar.enabled" to "true"), "baz")
        assertNotNull(context.getBean(TestFactory.Foo::class.java))
        assertNotNull(context.getBean(TestFactory.Bar::class.java))
        assertNotNull(context.getBean(TestFactory.Baz::class.java))
    }

    @Test
    fun buildAndStartWithPropertySourceAndEnvironment() {
        val context = buildAndStart<TestFactory>(
                MapPropertySource(
                        "bar",
                        mapOf("bar.enabled" to "true")
                ), "baz"
        )
        assertNotNull(context.getBean(TestFactory.Foo::class.java))
        assertNotNull(context.getBean(TestFactory.Bar::class.java))
        assertNotNull(context.getBean(TestFactory.Baz::class.java))
    }

    @Factory
    class TestFactory : AutoCloseable {

        override fun close() {
        }

        @Prototype
        class Foo

        @Prototype
        @Requires(property = "bar.enabled", value = "true")
        class Bar

        @Prototype
        @Requires(env = ["baz"])
        class Baz
    }
}