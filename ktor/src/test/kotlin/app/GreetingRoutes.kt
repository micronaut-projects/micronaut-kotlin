package app

import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.http.ContentType
import io.micronaut.ktor.KtorApplicationBuilder
import javax.inject.Singleton

@Singleton
class GreetingRoutes(private val greetingService: GreetingService) : KtorApplicationBuilder({
    routing {
        get("/") {
            call.respondText(greetingService.greet(), ContentType.Text.Plain)
        }
        get("/demo") {
            call.respondText(greetingService.greet())
        }
    }
})