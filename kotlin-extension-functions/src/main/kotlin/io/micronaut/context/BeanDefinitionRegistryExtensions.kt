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
package io.micronaut.context

import io.micronaut.inject.BeanDefinition
import io.micronaut.inject.qualifierByStereotype
import io.micronaut.inject.qualifiers.Qualifiers
import kotlin.reflect.KClass

/**
 * Extension for [BeanDefinitionRegistry.getBeanDefinition] providing a `getStereotypedBeanDefinition<Foo, Bar>()` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @return The [BeanDefinition]
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanDefinitionRegistry.getStereotypedBeanDefinition(): BeanDefinition<T> =
        getBeanDefinition(T::class.java, qualifierByStereotype<T, Q>())

/**
 * Extension for [BeanDefinitionRegistry.containsBean] providing a `containsBean<Foo>()` variant.
 *
 * @param T The bean type
 * @return True if contained
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanDefinitionRegistry.containsBean() = containsBean(T::class.java)

/**
 * Extension for [BeanDefinitionRegistry.containsBean] providing a `Foo::class in registry` variant.
 *
 * @param t The bean type
 * @return True if contained
 * @author Alejandro Gomez
 * @since 1.0.0
 */
operator fun BeanDefinitionRegistry.contains(t: KClass<out Any>) = containsBean(t.java)

/**
 * Extension for [BeanDefinitionRegistry.containsBean] providing a `containsStereotypedBean<Foo, Bar>()` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @return True if contained
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanDefinitionRegistry.containsStereotypedBean() =
        containsBean(T::class.java, qualifierByStereotype<T, Q>())

/**
 * Extension for [BeanDefinitionRegistry.containsBean] providing a `(Foo::class to Prototype::class) in registry` variant.
 *
 * @param t The pair containing the bean type and the stereotype type
 * @return True if contained
 * @author Alejandro Gomez
 * @since 1.0.0
 */
operator fun BeanDefinitionRegistry.contains(t: Pair<KClass<out Any>, KClass<out Annotation>>) =
        containsBean(t.first.java, Qualifiers.byStereotype(t.second.java))

/**
 * Extension for [BeanDefinitionRegistry.findBeanDefinition] providing a `findStereotypedBeanDefinition<Foo, Bar>()` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @return The [BeanDefinition] or null if not present
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanDefinitionRegistry.findStereotypedBeanDefinition(): BeanDefinition<T>? =
        findBeanDefinition(T::class.java, qualifierByStereotype<T, Q>()).orElse(null)

/**
 * Extension for [BeanDefinitionRegistry.getBeanDefinitions] providing a `getBeanDefinitions<Foo>()` variant.
 *
 * @param T The bean type
 * @return A [Collection] of the [BeanDefinition]s
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanDefinitionRegistry.getBeanDefinitions(): Collection<BeanDefinition<T>> = getBeanDefinitions(T::class.java)

/**
 * Extension for [BeanDefinitionRegistry.getBeanDefinitions] providing a `getStereotypedBeanDefinitions<Bar>()` variant.
 *
 * @param Q The stereotype type
 * @return A [Collection] of the [BeanDefinition]s
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified Q : Annotation> BeanDefinitionRegistry.getStereotypedBeanDefinitions(): Collection<BeanDefinition<out Any>> =
        getBeanDefinitions(qualifierByStereotype<Any, Q>())

/**
 * Extension for [BeanDefinitionRegistry.getBeanRegistrations] providing a `getBeanRegistrations<Foo>()` variant.
 *
 * @param T The bean type
 * @return A [Collection] of the [BeanRegistration]s
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanDefinitionRegistry.getBeanRegistrations(): Collection<BeanRegistration<T>> = getBeanRegistrations(T::class.java)

/**
 * Extension for [BeanDefinitionRegistry.getActiveBeanRegistrations] providing a `getActiveBeanRegistrations<Foo>()` variant.
 *
 * @param T The bean type
 * @return A [Collection] of the [BeanRegistration]s
 * @author Alejandro Gomez
 * @since 0.0.6
 */
inline fun <reified T> BeanDefinitionRegistry.getActiveBeanRegistrations(): Collection<BeanRegistration<T>> =
        getActiveBeanRegistrations(T::class.java)

/**
 * Extension for [BeanDefinitionRegistry.getActiveBeanRegistrations] providing a `getStereotypedActiveBeanRegistrations<Bar>()` variant.
 *
 * @param Q The stereotype type
 * @return A [Collection] of the [BeanRegistration]s
 * @author Alejandro Gomez
 * @since 0.0.6
 */
inline fun <reified Q : Annotation> BeanDefinitionRegistry.getStereotypedActiveBeanRegistrations(): Collection<BeanRegistration<out Any>> =
        getActiveBeanRegistrations(qualifierByStereotype<Any, Q>())

/**
 * Extension for [BeanDefinitionRegistry.findProxyTargetBeanDefinition] providing a `findProxyTargetBeanDefinition<Foo, Bar>()` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @return The original [BeanDefinition] or null if not present
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanDefinitionRegistry.findProxyTargetBeanDefinition(): BeanDefinition<T>? =
        findProxyTargetBeanDefinition(T::class.java, qualifierByStereotype<T, Q>()).orElse(null)

/**
 * Extension for [BeanDefinitionRegistry.findProxyBeanDefinition] providing a `findProxyBeanDefinition<Foo, Bar>()` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @return The original [BeanDefinition] or null if not present
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanDefinitionRegistry.findProxyBeanDefinition(): BeanDefinition<T>? =
        findProxyBeanDefinition(T::class.java, qualifierByStereotype<T, Q>()).orElse(null)

/**
 * Extension for [BeanDefinitionRegistry.getProxyTargetBeanDefinition] providing a `getProxyTargetBeanDefinition<Foo, Bar>()` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @return The original [BeanDefinition]
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanDefinitionRegistry.getProxyTargetBeanDefinition(): BeanDefinition<T> =
        getProxyTargetBeanDefinition(T::class.java, qualifierByStereotype<T, Q>())

/**
 * Extension for [BeanDefinitionRegistry.getBeanDefinition] providing a `getBeanDefinition<Bar>()` variant.
 *
 * @param T The bean type
 * @return The [BeanDefinition]
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanDefinitionRegistry.getBeanDefinition(): BeanDefinition<T> = getBeanDefinition(T::class.java)

/**
 * Extension for [BeanDefinitionRegistry.findBeanDefinition] providing a `findBeanDefinition<Bar>()` variant.
 *
 * @param T The bean type
 * @return The [BeanDefinition] or null if not present
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanDefinitionRegistry.findBeanDefinition(): BeanDefinition<T>? = findBeanDefinition(T::class.java).orElse(null)

/**
 * Extension for [BeanDefinitionRegistry.registerSingleton] providing a `registerStereotypedSingleton<Foo, Bar>(singleton, true)` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @param singleton The singleton bean
 * @param inject Whether the singleton should be injected
 * @return The [BeanDefinitionRegistry]
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanDefinitionRegistry.registerStereotypedSingleton(singleton: T, inject: Boolean): BeanDefinitionRegistry =
        registerSingleton(T::class.java, singleton, qualifierByStereotype<T, Q>(), inject)

/**
 * Extension for [BeanDefinitionRegistry.registerSingleton] providing a `registerStereotypedSingleton<Foo, Bar>(singleton)` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @param singleton The singleton bean
 * @return The [BeanDefinitionRegistry]
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanDefinitionRegistry.registerStereotypedSingleton(singleton: T): BeanDefinitionRegistry =
        registerSingleton(T::class.java, singleton, qualifierByStereotype<T, Q>())

/**
 * Extension for [BeanDefinitionRegistry.registerSingleton] providing a `registerNotStereotypedSingleton<Foo>(singleton)` variant.
 *
 * @param T The bean type
 * @param singleton The singleton bean
 * @return The [BeanDefinitionRegistry]
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanDefinitionRegistry.registerNotStereotypedSingleton(singleton: T): BeanDefinitionRegistry =
        registerSingleton(T::class.java, singleton)