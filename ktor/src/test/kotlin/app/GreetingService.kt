package app

import javax.inject.Singleton

@Singleton
class GreetingService {

    fun greet() : String {
        return "Hello World"
    }
}