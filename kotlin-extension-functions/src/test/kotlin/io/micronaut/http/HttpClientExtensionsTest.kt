/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.http

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.startApplication
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class HttpClientExtensionsTest {

    @Test
    fun testBlockingHttpClientExtensions() {
        // tag::usingClientFunctions[]
        val embeddedServer = ApplicationContext.run(EmbeddedServer::class.java)
        val client = embeddedServer.applicationContext.createBean(HttpClient::class.java, embeddedServer.url).toBlocking()

        // Test single object retrieve extension
        val getOneConventional = client.retrieve(HttpRequest.GET<Any>("/heroes/any"), Argument.of(Hero::class.java))
        val getOneReified = client.retrieveObject<Hero>(HttpRequest.GET<Any>("/heroes/any"))
        assertEquals(getOneConventional, getOneReified)

        // Test list retrieve extension
        val heroListConventional = client.retrieve(HttpRequest.GET<Any>("/heroes/list"), Argument.listOf(Hero::class.java))
        assertEquals(heroListConventional.size, 3)
        assertTrue(heroListConventional.find { it.alterEgo == "Diana Prince" } != null) // Let's make sure Wonder Woman is there!
        val heroListReified = client.retrieveList<Hero>(HttpRequest.GET<Any>("/heroes/list"))
        assertEquals(heroListConventional, heroListReified)
        val heroListByType : List<Hero> = client.retrieveList(HttpRequest.GET<Any>("/heroes/list"))
        assertEquals(heroListByType, heroListReified)
        // end::usingClientFunctions[]
        client.close()
        embeddedServer.close()
    }

    object Application

    @Test
    fun testStartApplication() {
        startApplication<Application>().use {
            assertTrue(it.containsBean(ObjectMapper::class.java))
        }
    }
}