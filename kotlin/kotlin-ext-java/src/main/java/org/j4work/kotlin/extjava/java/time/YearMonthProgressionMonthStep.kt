package org.j4work.kotlin.extjava.java.time

import org.j4work.kotlin.extjava.java.progression.ComparableProgressionDirection
import org.j4work.kotlin.extjava.java.progression.ComparableProgressionStep
import java.time.YearMonth

/**
 * A N-month step of a [YearMonth] progression where n is an integer.
 */
internal data class YearMonthProgressionMonthStep(
    val monthStep: Long
) : ComparableProgressionStep<YearMonth> {

    override val direction: ComparableProgressionDirection =
        ComparableProgressionDirection.ofStep(monthStep)

    /**
     * Progress [value] by [monthStep].
     */
    override fun progress(value: YearMonth): YearMonth =
        value.plusMonths(monthStep)

    /**
     *
     */
    override fun reverse() =
        YearMonthProgressionMonthStep(-monthStep)

    override fun toString(): String {
        return "$monthStep months"
    }
}
