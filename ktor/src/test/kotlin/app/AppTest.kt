package app

import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import javax.inject.Inject

@MicronautTest
class AppTest {

    @Inject
    @field:Client("/")
    lateinit var client : RxHttpClient

    @Test
    fun testApp() {
        assertEquals(
                "Hello World",
                client.toBlocking().retrieve("/demo")
        )
    }
}