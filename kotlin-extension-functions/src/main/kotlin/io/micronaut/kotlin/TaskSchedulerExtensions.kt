package io.micronaut.kotlin

import io.micronaut.scheduling.TaskScheduler
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