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
package io.micronaut.kotlin.http

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.hateoas.JsonError
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.jvm.optionals.getOrNull
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class HttpClientExtensionsTest {

    private lateinit var embeddedServer: EmbeddedServer
    private lateinit var client: BlockingHttpClient

    @BeforeEach
    fun setup() {
        embeddedServer = ApplicationContext.run(EmbeddedServer::class.java)
        client = embeddedServer.applicationContext.createBean(HttpClient::class.java, embeddedServer.url).toBlocking()
    }

    @Test
    fun testBlockingHttpClientExchangeSingleExtensions() {
        // tag::usingExchangeSingleResponseFunctions[]
        val exchangeOneConventional: HttpResponse<Hero> = client.exchange(HttpRequest.GET<Any>("/heroes/any"), Argument.of(Hero::class.java))
        val exchangeOneReified: HttpResponse<Hero> = client.exchangeObject<Hero>(HttpRequest.GET("/heroes/any"))
        // end::usingExchangeSingleResponseFunctions[]
        // tag::usingHttpMessageBodyOrNull[]
        val exchangeOneConventionalBody: Hero? = exchangeOneConventional.body.getOrNull()
        val exchangeOneReifiedBody: Hero? = exchangeOneReified.bodyOrNull
        // end::usingHttpMessageBodyOrNull[]
        assertNotNull(exchangeOneConventionalBody)
        assertNotNull(exchangeOneReifiedBody)
        assertEquals(exchangeOneConventionalBody, exchangeOneReifiedBody)
        assertEquals(exchangeOneConventional.status, exchangeOneReified.status)
    }


    @Test
    fun testBlockingHttpClientRetrieveSingleExtensions() {
        // tag::usingRetrieveSingleResponseFunctions[]
        val retrieveOneConventional: Hero = client.retrieve(HttpRequest.GET<Any>("/heroes/any"), Argument.of(Hero::class.java))
        val retrieveOneReified: Hero = client.retrieveObject<Hero>(HttpRequest.GET("/heroes/any"))
        // end::usingRetrieveSingleResponseFunctions[]
        assertEquals(retrieveOneConventional, retrieveOneReified)
    }

    @Test
    fun testBlockingHttpClientExchangeListExtensions() {
        // tag::usingExchangeListResponseFunctions[]
        val exchangeListConventional: HttpResponse<MutableList<Hero>> = client.exchange(HttpRequest.GET<Any>("/heroes/list"), Argument.listOf(Hero::class.java))
        val exchangeListReified: HttpResponse<List<Hero>> = client.exchangeList<Hero>(HttpRequest.GET("/heroes/list"))
        // end::usingExchangeListResponseFunctions[]
        val exchangeListConventionalBody = exchangeListConventional.body.getOrNull()
        val exchangeListReifiedBody = exchangeListConventional.bodyOrNull
        assertNotNull(exchangeListConventionalBody)
        assertEquals(exchangeListConventionalBody.size, 3)
        assertTrue(exchangeListConventionalBody.find { it.alterEgo == "Diana Prince" } != null) // Let's make sure Wonder Woman is there!
        assertNotNull(exchangeListReifiedBody)
        assertEquals(exchangeListConventionalBody, exchangeListReifiedBody)
        assertEquals(exchangeListConventional.status, exchangeListReified.status)
    }

    @Test
    fun testBlockingHttpClientRetrieveListExtensions() {
        // tag::usingRetrieveListResponseFunctions[]
        val retrieveListConventional: MutableList<Hero> = client.retrieve(HttpRequest.GET<Any>("/heroes/list"), Argument.listOf(Hero::class.java))
        val retrieveListReified: List<Hero> = client.retrieveList<Hero>(HttpRequest.GET("/heroes/list"))
        // end::usingRetrieveListResponseFunctions[]
        assertEquals(retrieveListConventional.size, 3)
        assertTrue(retrieveListConventional.find { it.alterEgo == "Diana Prince" } != null) // Let's make sure Wonder Woman is there!
        assertEquals(retrieveListConventional, retrieveListReified)
        val heroListByType: List<Hero> = client.retrieveList(HttpRequest.GET("/heroes/list"))
        assertEquals(heroListByType, retrieveListReified)
    }

    @Test
    fun testBlockingHttpClientExtensionsDefaultExceptionBody() {
        val exchangeOneConventionalException = assertThrows<HttpClientResponseException> {
            client.exchange(HttpRequest.GET<Any>("/heroes/missing-route"), Argument.of(Hero::class.java))
        }
        val exchangeOneReifiedException = assertThrows<HttpClientResponseException> {
            client.exchangeObject<Hero>(HttpRequest.GET("/heroes/missing-route"))
        }
        val exchangeOneConventionalExceptionBody = exchangeOneConventionalException.response.getBody(JsonError::class.java).getOrNull()
        val exchangeOneReifiedExceptionBody = exchangeOneReifiedException.response.getBodyObject<JsonError>()
        assertNotNull(exchangeOneConventionalExceptionBody)
        assertNotNull(exchangeOneReifiedExceptionBody)
        assertNull(exchangeOneConventionalException.response.body.getOrNull())
        assertNull(exchangeOneReifiedException.response.bodyOrNull)
        assertEquals(exchangeOneConventionalExceptionBody.message, exchangeOneReifiedExceptionBody.message)
        assertEquals(exchangeOneConventionalExceptionBody.message, exchangeOneReifiedExceptionBody.message)
        assertEquals(HttpStatus.NOT_FOUND, exchangeOneConventionalException.response.status)
        assertEquals(exchangeOneConventionalException.response.status, exchangeOneReifiedException.response.status)
    }

    @Test
    fun testBlockingHttpClientExtensionsCustomExceptionBody() {
        // tag::usingHttpMessageGetBodyFunctions[]
        val exchangeOneConventionalCustomException: HttpClientResponseException = assertThrows<HttpClientResponseException> {
            client.exchange(HttpRequest.GET<Any>("/heroes/conflict"), Argument.of(Hero::class.java))
        }
        val exchangeOneReifiedCustomException: HttpClientResponseException = assertThrows<HttpClientResponseException> {
            client.exchangeObject<Hero>(HttpRequest.GET("/heroes/conflict"))
        }
        val exchangeOneConventionalCustomExceptionBody: HeroJsonError? = exchangeOneConventionalCustomException.response.getBody(HeroJsonError::class.java).getOrNull()
        val exchangeOneReifiedCustomExceptionBody: HeroJsonError? = exchangeOneReifiedCustomException.response.getBodyObject<HeroJsonError>()
        // end::usingHttpMessageGetBodyFunctions[]
        assertNotNull(exchangeOneConventionalCustomExceptionBody)
        assertNotNull(exchangeOneReifiedCustomExceptionBody)
        assertNull(exchangeOneConventionalCustomException.response.body.getOrNull())
        assertNull(exchangeOneReifiedCustomException.response.bodyOrNull)
        assertEquals("conflict found is missing", exchangeOneConventionalCustomExceptionBody.testJsonErrorMessage)
        assertEquals(exchangeOneConventionalCustomExceptionBody, exchangeOneReifiedCustomExceptionBody)
        assertEquals(HttpStatus.CONFLICT, exchangeOneConventionalCustomException.response.status)
        assertEquals(
            exchangeOneConventionalCustomException.response.status,
            exchangeOneReifiedCustomException.response.status
        )
    }
}
