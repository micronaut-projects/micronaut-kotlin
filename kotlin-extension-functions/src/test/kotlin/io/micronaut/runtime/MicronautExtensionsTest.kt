package io.micronaut.runtime


import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Parameter
import io.micronaut.context.annotation.Prototype
import io.micronaut.context.createBean
import io.micronaut.context.getBean
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
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
        val customMappedErrorCode = 42
        val context = Micronaut.build().mapError<NullPointerException> { customMappedErrorCode }.build()
        assertNotNull(context)
        // TODO finish implementing this test
//        val client = context.createBean<HttpClient>().toBlocking()
//        val response = client.exchange<Any, Any>(HttpRequest.GET<Any>("/sample"))
//        assertEquals(response.status.code, customMappedErrorCode)
//        client.close()
//        context.close()
    }

    @Factory
    class TestFactory {

        @Prototype
        class Foo(@Parameter("baz") val baz: Int)

        @Singleton
        class Bar
    }

    @Controller("/sample")
    class SampleController {
        @Get
        fun testMapErrorHandler(): HttpResponse<String> {
            throw NullPointerException()
        }
    }
}