package io.micronaut.runtime


import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Parameter
import io.micronaut.context.annotation.Prototype
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import javax.inject.Singleton

/**
 * @author Alejandro Gomez
 */
class MicronautExtensionsTest {

    @Test
    fun mnRun() {
        val context = mnRun<TestFactory>("-baz=1")
        // TODO
        val foo = context.createBean(TestFactory.Foo::class.java, mapOf("baz" to 1))
        assertNotNull(foo)
        assertEquals(1, foo.baz)
        assertNotNull(context.getBean(TestFactory.Bar::class.java))
    }

    @Test
    fun mainClass() {
        val context = Micronaut.build().mainClass<TestFactory>().build()
        assertNotNull(context)
        // TODO
        // assertNotNull(context.getBean(TestFactory.Bar::class.java))
    }

    @Test
    fun mapError() {
        val context = Micronaut.build().mapError<NullPointerException> { 1 }.build()
        assertNotNull(context)
        // TODO
    }

    @Factory
    class TestFactory {

        @Prototype
        class Foo(@Parameter("baz") val baz: Int)

        @Singleton
        class Bar
    }
}