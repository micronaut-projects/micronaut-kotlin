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
        val result = qualifierByAnnotation<Any, Context>(metadata)
        // then
        assertEquals(result::class, Qualifiers.byAnnotation<Context>(metadata, Context::class.java)::class)
    }

    @Test
    fun qualifierByName() {
        // given
        val name = "foo"
        // when
        val result = qualifierByName<Any>(name)
        // then
        assertEquals(name, (result as Named).name)
    }
}
