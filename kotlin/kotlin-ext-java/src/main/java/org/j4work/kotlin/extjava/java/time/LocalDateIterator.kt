package org.j4work.kotlin.extjava.java.time

import java.time.LocalDate
import java.time.temporal.TemporalAmount

/**
 * Iterate between [startDate] and [endDateInclusive] with step [step].
 */
class LocalDateIterator(
    private val startDate: LocalDate,
    private val endDateInclusive: LocalDate,
    private val step: TemporalAmount
) : Iterator<LocalDate> {

    /**
     * Current state of iterator.
     */
    private var currentDate =
        startDate

    override fun hasNext() =
        currentDate <= endDateInclusive

    override fun next(): LocalDate {
        val next = currentDate
        currentDate = currentDate.plus(step)
        return next
    }
}