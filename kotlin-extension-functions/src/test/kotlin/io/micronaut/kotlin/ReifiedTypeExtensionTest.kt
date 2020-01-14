package io.micronaut.kotlin

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.http.client.HttpClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ReifiedTypeExtensionTest {

    @Test
    fun testBlockingHttpClient() {
        val embeddedServer = ApplicationContext.run(EmbeddedServer::class.java)
        val client = embeddedServer.applicationContext.createBean(HttpClient::class.java, embeddedServer.url)
        val heroListConventional = client.toBlocking().retrieve(HttpRequest.GET<Any>("/books"), Argument.listOf(Hero::class.java))
        assertEquals(heroListConventional.size, 3)
        assertTrue(heroListConventional.find { it.alterEgo == "Diana Prince" } != null)
        val heroListReified = client.toBlocking().retrieveList<Hero>(HttpRequest.GET<Any>("/books"))
        assertEquals(heroListConventional, heroListReified)

    }
}