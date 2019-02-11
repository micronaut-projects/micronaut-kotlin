package app

// tag::imports[]
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.*
import io.micronaut.ktor.KtorApplicationBuilder
import javax.inject.Singleton
// end::imports[]

// tag::class[]
@Singleton
class GreetingRoutes(val greetingService: GreetingService) : KtorApplicationBuilder({ // <1>
    routing { // <2>
        get("/") {
            call.respond(greetingService.greet())
        }
        get("/demo") {
            call.respond(greetingService.greet())
        }
    }
})
// end::class[]