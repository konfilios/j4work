package org.j4work.kotlin.extjava.java.time

import java.time.LocalDate
import java.time.temporal.TemporalAmount

/**
 * Create a new [LocalDateProgression] covering [this] closed range with given [step].
 */
infix fun ClosedRange<LocalDate>.step(step: TemporalAmount) =
    LocalDateProgression(start, endInclusive, step)