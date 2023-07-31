package example.micronaut

import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import reactor.core.publisher.Mono

@Singleton
class GreetingService {

    @Value("\${greeting.suffix}")
    var suffix: String? = null

    fun sayHi(name: String): Mono<Greeting> {
        return Mono.just(Greeting("Hello $name${suffix}"))
    }
}
