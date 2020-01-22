package io.micronaut.context


import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Parameter
import io.micronaut.context.annotation.Prototype
import io.micronaut.context.annotation.Requires
import io.micronaut.inject.qualifiers.Qualifiers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Singleton

/**
 * @author Alejandro Gomez
 */
class BeanDefinitionRegistryExtensionsTest {

    private lateinit var context: BeanContext

    @BeforeEach
    fun setUp() {
        context = ApplicationContext.build(TestFactory::class.java).start()
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
        assertEquals(context.containsBean(TestFactory.Foo::class.java, Qualifiers.byStereotype(Singleton::class.java)),
                context.containsStereotypedBean<TestFactory.Foo, Singleton>())
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
        assertEquals(context.findProxyTargetBeanDefinition(TestFactory.Foo::class.java, Qualifiers.byStereotype<TestFactory.Foo>(Prototype::class.java))
                .orElse(null), context.findProxyTargetBeanDefinition<TestFactory.Foo, Prototype>())
    }

    @Test
    fun findProxyBeanDefinition() {
        assertEquals(context.findProxyBeanDefinition(TestFactory.Foo::class.java, Qualifiers.byStereotype<TestFactory.Foo>(Prototype::class.java))
                .orElse(null), context.findProxyBeanDefinition<TestFactory.Foo, Prototype>())
    }

    @Test
    fun getProxyTargetBeanDefinition() {
        assertEquals(context.getProxyTargetBeanDefinition(TestFactory.Foo::class.java, Qualifiers.byStereotype<TestFactory.Foo>(Prototype::class.java)),
                context.getProxyTargetBeanDefinition<TestFactory.Foo, Prototype>())
    }

    @Test
    fun findBeanDefinition() {
        assertEquals(context.findBeanDefinition(TestFactory.Foo::class.java).orElse(null), context.findBeanDefinition<TestFactory.Foo>())
    }

    @Test
    fun registerStereotypedSingleton() {
        val singleton = TestFactory.Baz()
        context.registerStereotypedSingleton<TestFactory.Baz, Singleton>(singleton)
        assertSame(singleton, context.getBean(TestFactory.Baz::class.java))
        assertNull(context.getBean(TestFactory.Baz::class.java).foo)
    }

    @Test
    fun registerStereotypedSingletonWithInjectionDisabled() {
        val singleton = TestFactory.Baz()
        context.registerStereotypedSingleton<TestFactory.Baz, Singleton>(singleton, false)
        assertSame(singleton, context.getBean(TestFactory.Baz::class.java))
        assertNull(context.getBean(TestFactory.Baz::class.java).foo)
    }

    @Test
    fun registerStereotypedSingletonWithInjectionEnabled() {
        val singleton = TestFactory.Baz()
        context.registerStereotypedSingleton<TestFactory.Baz, Singleton>(singleton, true)
        assertSame(singleton, context.getBean(TestFactory.Baz::class.java))
        // TODO figure out why this isn't working
//        assertNotNull(context.getBean(TestFactory.Baz::class.java).foo)
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