package app

// tag::imports[]
import javax.inject.Singleton
// end::imports[]

// tag::class[]
@Singleton
class GreetingService {
    fun greet(name: String = "World"): Greeting {
        return Greeting("Hello $name")
    }
}

data class Greeting(val message: String)
// end::class[]
