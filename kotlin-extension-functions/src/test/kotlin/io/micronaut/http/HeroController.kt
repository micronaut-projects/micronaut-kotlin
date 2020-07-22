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

import io.micronaut.http.Hero
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.time.LocalDateTime

@Controller("/heroes")
class HeroController {

    @Get("/any")
    fun getOne(): HttpResponse<Hero> {
        return HttpResponse.ok(
                Hero(
                        0L,
                        "Black Panther",
                        "T'Challa",
                        LocalDateTime.of(1966, 7, 1, 0, 0)
                )
        )
    }

    @Get("/list")
    fun list(): HttpResponse<List<Hero>> {
        return HttpResponse.ok(listOf(
                Hero(
                        1L,
                        "Iron Man",
                        "Tony Stark",
                        LocalDateTime.of(1963, 3, 1, 0, 0)
                ),
                Hero(
                        2L,
                        "Batman",
                        "Bruce Wayne",
                        LocalDateTime.of(1939, 5, 1, 0, 0)
                ),
                Hero(
                        3L,
                        "Wonder Woman",
                        "Diana Prince",
                        LocalDateTime.of(1941, 10, 1, 0, 0)
                )
        ))
    }
}