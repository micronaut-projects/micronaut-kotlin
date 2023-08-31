package example.micronaut

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

@MicronautTest
class KotlinGraalTest {

    @Inject
    lateinit var client: GreetingClient

    @Test
    fun testHelloGet() {
        println("jvmci.Compiler: " + System.getProperty("jvmci.Compiler"))
        println("java.vendor.version: " + System.getProperty("java.vendor.version"))
        println("java.vendor: " + System.getProperty("java.vendor"))

        Assertions.assertEquals(
                "Hello World!",
                Flux.from(client.hello("World")).blockFirst()!!.message
        )
    }

}

@Client("/")
interface GreetingClient {

    @Get("/hello/{name}")
    fun hello(name: String): Publisher<Greeting>
}
