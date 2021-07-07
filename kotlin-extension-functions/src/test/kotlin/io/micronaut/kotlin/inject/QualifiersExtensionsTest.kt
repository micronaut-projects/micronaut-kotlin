/*
 * Copyright 2017-2020 original authors
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
package io.micronaut.kotlin.inject

import io.micronaut.context.annotation.Context
import io.micronaut.core.annotation.AnnotationMetadata
import io.micronaut.core.naming.Named
import io.micronaut.inject.qualifiers.Qualifiers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * @author Alejandro Gomez
 */
class QualifiersExtensionsTest {

    @Test
    fun qualifierByStereotype() {
        // given
        // when
        val result = io.micronaut.kotlin.inject.qualifierByStereotype<Any, Context>()
        // then
        assertEquals(result::class, Qualifiers.byStereotype<Any>(Context::class.java)::class)
    }

    @Test
    fun qualifierByAnnotation() {
        // given
        val metadata = AnnotationMetadata.EMPTY_METADATA
        // when
        val result = io.micronaut.kotlin.inject.qualifierByAnnotation<Any, Context>(metadata)
        // then
        assertEquals(result::class, Qualifiers.byAnnotation<Context>(metadata, Context::class.java)::class)
        assertEquals((result as Named).name, (Qualifiers.byAnnotation<Context>(metadata, Context::class.java) as Named).name)
    }

    @Test
    fun qualifierByName() {
        // given
        val name = "foo"
        // when
        val result = io.micronaut.kotlin.inject.qualifierByName<Any>(name)
        // then
        assertEquals(name, (result as Named).name)
    }
}
