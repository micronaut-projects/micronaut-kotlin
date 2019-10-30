package app

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.micronaut.ktor.KtorRoutingBuilder
import javax.inject.Singleton

@Singleton
class GreetingRoutes(private val greetingService: GreetingService) : KtorRoutingBuilder({
    authenticate {
        get("/authenticated") {
            call.respondText("Hello from authenticated route")
        }
    }

    get("/") {
        call.respondText(greetingService.greet(), ContentType.Text.Plain)
    }

    get("/demo") {
        call.respondText(greetingService.greet())
    }
})
