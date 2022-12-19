/*
 * Copyright 2017-2022 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.kotlin.converters

import io.micronaut.core.convert.MutableConversionService
import io.micronaut.core.convert.TypeConverterRegistrar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.asFlux
import reactor.core.publisher.Flux

/**
 * Converts between a {@link Flow} and a {@link Flux}.
 *
 * @author Konrad Kamiński
 * @since 1.3
 */
class FlowReactorConverterRegistrar : TypeConverterRegistrar {
    override fun register(conversionService: MutableConversionService) {
        conversionService.addConverter(
            Flow::class.java,
            Flux::class.java
        ) { flow -> (flow as Flow<Any>).asFlux() }
    }
}
