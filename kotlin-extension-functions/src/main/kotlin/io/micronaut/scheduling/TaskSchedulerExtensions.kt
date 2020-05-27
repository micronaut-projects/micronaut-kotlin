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
package io.micronaut.scheduling

import java.time.Duration
import java.util.concurrent.Callable
import java.util.concurrent.ScheduledFuture

/**
 * Extension for [TaskScheduler.schedule] providing a `scheduleCallable("0 0 12 * * ?") { doWork() }` variant.
 *
 * @param cron The cron expression
 * @param command The task to execute
 * @return a [ScheduledFuture] that can be used to extract the result or cancel
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <V> TaskScheduler.scheduleCallable(cron: String, crossinline command: () -> V): ScheduledFuture<V> =
        schedule(cron, Callable<V> { command.invoke() })

/**
 * Extension for [TaskScheduler.schedule] providing a `scheduleCallable(delay) { doWork() }` variant.
 *
 * @param delay The time from now to delay execution
 * @param command The task to execute
 * @return a [ScheduledFuture] that can be used to the extract result or cancel
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <V> TaskScheduler.scheduleCallable(delay: Duration, crossinline command: () -> V): ScheduledFuture<V> =
        schedule(delay, Callable<V> { command.invoke() })