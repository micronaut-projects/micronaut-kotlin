package app

// tag::imports[]
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import io.micronaut.ktor.KtorRoutingBuilder
import javax.inject.Singleton
// end::imports[]

// tag::class[]
@Singleton
class GreetingRoutes(private val greetingService: GreetingService) : KtorRoutingBuilder({ // <1>
    get("/") {
        call.respond(greetingService.greet())
    }

    post("/") {
        val name = call.receive<CustomGreetingRequest>().name

        call.respond(greetingService.greet(name))
    }
})

data class CustomGreetingRequest(val name: String)
// end::class[]
