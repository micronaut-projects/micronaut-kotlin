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
package io.micronaut.context.env.hocon.nativeimage

import com.oracle.svm.core.annotate.AutomaticFeature
import io.micronaut.context.env.hocon.HoconPropertySourceLoaderImpl
import io.micronaut.core.annotation.Internal
import org.graalvm.nativeimage.hosted.Feature
import org.graalvm.nativeimage.hosted.Feature.BeforeAnalysisAccess
import org.graalvm.nativeimage.hosted.RuntimeReflection

/**
 * Kotlin feature to configure platform initialization and reflection details for native image.
 *
 * @author Marcelo Liberato
 * @since 2.0.1
 */
@AutomaticFeature
@Internal
class HoconFeature: Feature {

    override fun beforeAnalysis(access: BeforeAnalysisAccess) {
        access.findClassByName("com.typesafe.config.Config")?.let {
            registerClassForRuntimeReflection(HoconPropertySourceLoaderImpl::class.java)
        }
    }

    private fun registerClassForRuntimeReflection(clazz: Class<*>) {
        RuntimeReflection.register(clazz)
        RuntimeReflection.registerForReflectiveInstantiation(clazz)
    }

}
