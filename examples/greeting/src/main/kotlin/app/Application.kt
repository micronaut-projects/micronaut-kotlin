package app

// tag::imports[]
import io.ktor.server.netty.NettyApplicationEngine
import io.micronaut.ktor.*
import org.slf4j.LoggerFactory
import javax.inject.Singleton
// end::imports[]

// tag::class[]
@Singleton
class Application : KtorApplication<NettyApplicationEngine.Configuration>({ // <1>
    applicationEngineEnvironment { // <2>
        log = LoggerFactory.getLogger(Application::class.java)
    }

    applicationEngine { // <3>
        workerGroupSize = 10
    }
})

fun main(args: Array<String>) { // <4>
    runApplication(args)
}
// end::class[]