package app


import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.NettyApplicationEngine
import io.micronaut.ktor.KotrApplication
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class App(val greetingService: GreetingService) : KotrApplication<NettyApplicationEngine.Configuration>({
    routing {
        get("/") {
            call.respondText(greetingService.greet(), ContentType.Text.Plain)
        }
        get("/demo") {
            call.respondText(greetingService.greet())
        }
    }
}) {
    init {
        applicationEngineEnvironment {
            log = LoggerFactory.getLogger(App::class.java)
        }

        applicationEngine {
            workerGroupSize = 10
        }
    }
}