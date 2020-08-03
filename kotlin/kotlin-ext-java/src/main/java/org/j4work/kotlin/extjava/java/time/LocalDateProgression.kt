package org.j4work.kotlin.extjava.java.time

import java.time.LocalDate
import java.time.temporal.TemporalAmount

/**
 * A progression between [start] and [endInclusive] with step [step].
 */
class LocalDateProgression(
    override val start: LocalDate,
    override val endInclusive: LocalDate,
    private val step: TemporalAmount
) :
    Iterable<LocalDate>, ClosedRange<LocalDate> {

    override fun iterator(): Iterator<LocalDate> =
        LocalDateIterator(start, endInclusive, step)

    /**
     * Create a new progression with given [newStep]
     */
    infix fun step(newStep: TemporalAmount) =
        LocalDateProgression(start, endInclusive, newStep)
}

