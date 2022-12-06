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
package io.micronaut.kotlin.context

import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.*
import io.micronaut.context.exceptions.NoSuchBeanException
import io.micronaut.inject.qualifiers.Qualifiers
import io.micronaut.runtime.context.scope.Refreshable
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.stream.Collectors

/**
 * @author Alejandro Gomez
 */
@MicronautTest
class BeanContextTest {

    @Inject
    private lateinit var context: BeanContext

    @Test
    fun createBean() {
        val foo1 = context.createBean<TestFactory.Foo>()
        assertSame(TestFactory.Foo::class, foo1::class)
        val foo2: TestFactory.Foo = context.createBean()
        assertSame(TestFactory.Foo::class, foo2::class)
    }

    @Test
    fun createStereotypedBean() {
        val foo = context.createStereotypedBean<TestFactory.Foo, Prototype>()
        assertSame(TestFactory.Foo::class, foo::class)
    }

    @Test
    fun createStereotypedBeanWithArguments() {
        val bar = context.createStereotypedBean<TestFactory.Bar, Prototype>(mapOf("baz" to 1))
        assertSame(TestFactory.Bar::class, bar::class)
        assertEquals(1, bar.baz)
    }

    @Test
    fun createStereotypedBeanWithVarArg() {
        val bar = context.createStereotypedBean<TestFactory.Bar, Prototype>(2)
        assertSame(TestFactory.Bar::class, bar::class)
        assertEquals(2, bar.baz)
    }

    @Test
    fun createBeanWithArguments() {
        val bar = context.createBean<TestFactory.Bar>(mapOf("baz" to 1))
        assertSame(TestFactory.Bar::class, bar::class)
        assertEquals(1, bar.baz)
    }

    @Test
    fun createBeanWithVarArg() {
        val bar = context.createBean<TestFactory.Bar>(2)
        assertSame(TestFactory.Bar::class, bar::class)
        assertEquals(2, bar.baz)
    }

    @Test
    fun destroyBean() {
        val baz = context.destroyBean<TestFactory.Baz>()
        assertSame(TestFactory.Baz::class, baz!!::class)
    }

    @Test
    fun getBean() {
        assertThrows<NoSuchBeanException> { context.getBean<TestFactory.Qux>() }
        assertNotNull(context.getBean<TestFactory.Foo>())
    }

    @Test
    fun getBeanByName() {
        assertThrows<NoSuchBeanException> { context.getBean<TestFactory.Foo>("invalid") }
        assertThrows<NoSuchBeanException> { context.getBean<TestFactory.Qux>("foo") }
        assertNotNull(context.getBean<TestFactory.Foo>("foo"))
    }

    @Test
    fun getStereotypedBean() {
        assertThrows<NoSuchBeanException> { context.getStereotypedBean<TestFactory.Qux, Context>() }
        assertNotNull(context.getStereotypedBean<TestFactory.Foo, Prototype>())
    }

    @Test
    fun findBean() {
        assertNull(context.findBean<TestFactory.Qux>())
        assertNotNull(context.findBean<TestFactory.Foo>())
    }

    @Test
    fun findStereotypedBean() {
        assertNull(context.findStereotypedBean<TestFactory.Qux, Context>())
        assertNotNull(context.findStereotypedBean<TestFactory.Foo, Prototype>())
    }

    @Test
    fun getBeansOfType() {
        assertIterableEquals(context.getBeansOfType(TestFactory.Baz::class.java), context.getBeansOfType<TestFactory.Baz>())
        assertIterableEquals(context.getBeansOfType(TestFactory.Qux::class.java), context.getBeansOfType<TestFactory.Qux>())
    }

    @Test
    fun getStereotypedBeansOfType() {
        assertIterableEquals(context.getBeansOfType(TestFactory.Baz::class.java, Qualifiers.byStereotype(Context::class.java)),
                context.getStereotypedBeansOfType<TestFactory.Baz, Context>())
        assertIterableEquals(context.getBeansOfType(TestFactory.Qux::class.java, Qualifiers.byStereotype(Context::class.java)),
                context.getStereotypedBeansOfType<TestFactory.Qux, Context>())
    }

    @Test
    fun streamOfType() {
        val bazes = context.streamOfType(TestFactory.Baz::class.java).collect(Collectors.toList())
        assertIterableEquals(bazes, context.streamOfType<TestFactory.Baz>().collect(Collectors.toList()))
        val quxes = context.streamOfType(TestFactory.Qux::class.java).collect(Collectors.toList())
        assertIterableEquals(quxes, context.streamOfType<TestFactory.Qux>().collect(Collectors.toList()))
    }

    @Test
    fun sequenceOfType() {
        val bazes = context.streamOfType(TestFactory.Baz::class.java).collect(Collectors.toList())
        assertIterableEquals(bazes, context.sequenceOfType<TestFactory.Baz>().toList())
        val quxes = context.streamOfType(TestFactory.Qux::class.java).collect(Collectors.toList())
        assertIterableEquals(quxes, context.sequenceOfType<TestFactory.Qux>().toList())
    }

    @Test
    fun streamOfStereotypedType() {
        val bazes = context.streamOfType(TestFactory.Baz::class.java, Qualifiers.byStereotype(Context::class.java)).collect(Collectors.toList())
        assertIterableEquals(bazes, context.streamOfStereotypedType<TestFactory.Baz, Context>().collect(Collectors.toList()))
        val quxes = context.streamOfType(TestFactory.Qux::class.java, Qualifiers.byStereotype(Context::class.java)).collect(Collectors.toList())
        assertIterableEquals(quxes, context.streamOfStereotypedType<TestFactory.Qux, Context>().collect(Collectors.toList()))
    }

    @Test
    fun sequenceOfStereotypedType() {
        val bazes = context.streamOfType(TestFactory.Baz::class.java, Qualifiers.byStereotype(Context::class.java)).collect(Collectors.toList())
        assertIterableEquals(bazes, context.sequenceOfStereotypedType<TestFactory.Baz, Context>().toList())
        val quxes = context.streamOfType(TestFactory.Qux::class.java, Qualifiers.byStereotype(Context::class.java)).collect(Collectors.toList())
        assertIterableEquals(quxes, context.sequenceOfStereotypedType<TestFactory.Qux, Context>().toList())
    }

    @Test
    fun getProxyTargetBean() {
        assertSame(context.getProxyTargetBean(TestFactory.Stuff::class.java, Qualifiers.byStereotype(Refreshable::class.java))::class,
                context.getProxyTargetBean<TestFactory.Stuff, Refreshable>()::class)
    }

    @Test
    fun findOrInstantiateBean() {
        assertSame(context.findOrInstantiateBean(TestFactory.Foo::class.java).get()::class, context.findOrInstantiateBean<TestFactory.Foo>()!!::class)
        assertSame(context.findOrInstantiateBean(TestFactory.Baz::class.java).get()::class, context.findOrInstantiateBean<TestFactory.Baz>()!!::class)
    }

    @Factory
    class TestFactory {

        @Prototype
        class Foo

        @Prototype
        class Bar(@Parameter("baz") val baz: Int)

        @Context
        class Baz

        @Context
        @Requires(property = "qux.enabled")
        class Qux

        @Refreshable
        open class Stuff
    }
}
