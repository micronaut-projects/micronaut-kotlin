package example.micronaut

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import reactor.core.publisher.Mono

@Controller("/")
class HelloController(private val greetingService: GreetingService) {

    @Get("/hello/{name}")
    fun sayHi(name: String): Mono<Greeting> {
        return greetingService.sayHi(name)
    }
}
