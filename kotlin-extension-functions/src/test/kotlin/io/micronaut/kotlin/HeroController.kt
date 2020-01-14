package io.micronaut.kotlin

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.time.LocalDateTime

@Controller("/books")
class HeroController {

    @Get
    fun list(): HttpResponse<List<Hero>> {
        return HttpResponse.ok(listOf(
                Hero(
                        1L,
                        "Iron Man",
                        "Tony Stark",
                        LocalDateTime.of(1963,3,1,0,0)
                ),
                Hero(
                        2L,
                        "Batman",
                        "Bruce Wayne",
                        LocalDateTime.of(1939,5,1,0,0)
                ),
                Hero(
                        3L,
                        "Wonder Woman",
                        "Diana Prince",
                        LocalDateTime.of(1941,10,1,0,0)
                )
        ))
    }
}