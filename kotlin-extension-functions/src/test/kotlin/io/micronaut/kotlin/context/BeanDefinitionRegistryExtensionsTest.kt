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


import io.micronaut.context.ApplicationContext
import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Parameter
import io.micronaut.context.annotation.Prototype
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.AnnotationUtil
import io.micronaut.inject.qualifiers.Qualifiers
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/**
 * @author Alejandro Gomez
 */
class BeanDefinitionRegistryExtensionsTest {

    private lateinit var context: BeanContext

    @BeforeEach
    fun setUp() {
        context = ApplicationContext.builder(TestFactory::class.java).start()
    }

    @Test
    fun getStereotypedBeanDefinition() {
        assertEquals(context.getBeanDefinition(TestFactory.Foo::class.java, Qualifiers.byStereotype(Prototype::class.java)),
                context.getStereotypedBeanDefinition<TestFactory.Foo, Prototype>())
    }

    @Test
    fun containsBean() {
        assertTrue(context.containsBean<TestFactory.Foo>())
        assertFalse(context.containsBean<String>())
    }

    @Test
    fun contains() {
        assertFalse(TestFactory.Qux::class in context)
        assertTrue(TestFactory.Foo::class in context)
    }

    @Test
    fun containsStereotypedBean() {
        assertEquals(context.containsBean(TestFactory.Foo::class.java, Qualifiers.byStereotype(Prototype::class.java)),
                context.containsStereotypedBean<TestFactory.Foo, Prototype>())
        assertEquals(context.containsBean(TestFactory.Foo::class.java, Qualifiers.byStereotype(AnnotationUtil.SINGLETON)),
                context.containsStereotypedBean<TestFactory.Foo>(AnnotationUtil.SINGLETON))
    }

    @Test
    fun containsStereotyped() {
        assertFalse((TestFactory.Qux::class to Prototype::class) in context)
        assertTrue((TestFactory.Foo::class to Prototype::class) in context)
    }

    @Test
    fun findStereotypedBeanDefinition() {
        assertEquals(context.findBeanDefinition(TestFactory.Foo::class.java, Qualifiers.byStereotype(Prototype::class.java)).orElse(null),
                context.findStereotypedBeanDefinition<TestFactory.Foo, Prototype>())
    }

    @Test
    fun getBeanDefinitions() {
        assertTrue(context.getBeanDefinitions<TestFactory.Foo>().containsAll(context.getBeanDefinitions(TestFactory.Foo::class.java)))
    }

    @Test
    fun getStereotypedBeanDefinitions() {
        assertEquals(context.getBeanDefinitions(Qualifiers.byStereotype(Prototype::class.java)), context.getStereotypedBeanDefinitions<Prototype>())
    }

    @Test
    fun getActiveBeanRegistrations() {
        assertEquals(context.getActiveBeanRegistrations(TestFactory.Foo::class.java), context.getActiveBeanRegistrations<TestFactory.Foo>())
    }

    @Test
    fun getStereotypedActiveBeanRegistrations() {
        assertEquals(context.getActiveBeanRegistrations(Qualifiers.byStereotype<Prototype>(Prototype::class.java)),
                context.getStereotypedActiveBeanRegistrations<Prototype>())
    }

    @Test
    fun getBeanRegistrations() {
        assertEquals(context.getBeanRegistrations(TestFactory.Baz::class.java), context.getBeanRegistrations<TestFactory.Baz>())
    }

    @Test
    fun findProxyTargetBeanDefinition() {
        assertEquals(context.findProxyTargetBeanDefinition(TestFactory.Foo::class.java, Qualifiers.byStereotype(Prototype::class.java))
                .orElse(null), context.findProxyTargetBeanDefinition<TestFactory.Foo, Prototype>())
    }

    @Test
    fun findProxyBeanDefinition() {
        assertEquals(context.findProxyBeanDefinition(TestFactory.Foo::class.java, Qualifiers.byStereotype(Prototype::class.java))
                .orElse(null), context.findProxyBeanDefinition<TestFactory.Foo, Prototype>())
    }

    @Test
    fun getProxyTargetBeanDefinition() {
        assertEquals(context.getProxyTargetBeanDefinition(TestFactory.Foo::class.java, Qualifiers.byStereotype(Prototype::class.java)),
                context.getProxyTargetBeanDefinition<TestFactory.Foo, Prototype>())
    }

    @Test
    fun findBeanDefinition() {
        assertEquals(context.findBeanDefinition(TestFactory.Foo::class.java).orElse(null), context.findBeanDefinition<TestFactory.Foo>())
    }

    @Test
    fun registerStereotypedSingleton() {
        val singleton = TestFactory.Baz()
        context.registerNotStereotypedSingleton(singleton)
        assertSame(singleton, context.getBean(TestFactory.Baz::class.java))
        assertNull(context.getBean(TestFactory.Baz::class.java).foo)
    }

    @Test
    @Disabled("Core bug needs fixing? io.micronaut.context.exceptions.NonUniqueBeanException: Multiple possible bean candidates found: [Baz, Baz]")
    fun registerStereotypedSingletonWithInjectionDisabled() {
        val singleton = TestFactory.Baz()
        context.registerStereotypedSingleton(singleton, AnnotationUtil.SINGLETON, false)
        assertSame(singleton, context.getBean(TestFactory.Baz::class.java))
        assertNull(context.getBean(TestFactory.Baz::class.java).foo)
    }

    @Test
    fun registerStereotypedSingletonWithInjectionEnabled() {
        val singleton = TestFactory.Baz(TestFactory.Foo())
        context.registerStereotypedSingleton(singleton, AnnotationUtil.SINGLETON, true)
        assertSame(singleton, context.getBean(TestFactory.Baz::class.java))
        assertNotNull(context.getBean(TestFactory.Baz::class.java).foo)
    }

    @Test
    fun registerNotStereotypedSingleton() {
        val singleton = TestFactory.Baz()
        context.registerNotStereotypedSingleton(singleton)
        assertSame(singleton, context.getBean(TestFactory.Baz::class.java))
    }

    @Factory
    class TestFactory {

        @Prototype
        class Foo

        @Prototype
        class Bar(@Parameter("baz") val baz: Int)

        @Singleton
        class Baz(val foo: Foo? = null)

        @Context
        @Requires(property = "qux.enabled")
        class Qux
    }
}
