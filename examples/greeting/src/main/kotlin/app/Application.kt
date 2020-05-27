/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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