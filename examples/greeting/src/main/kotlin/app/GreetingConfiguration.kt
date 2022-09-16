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
package app

// tag::imports[]
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.jackson.jackson
import io.micronaut.ktor.KtorApplicationBuilder
import jakarta.inject.Singleton
// end::imports[]

// tag::class[]
@Singleton
class GreetingConfiguration : KtorApplicationBuilder({ // <1>
    install(ContentNegotiation) { // <2>
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
})
// end::class[]
