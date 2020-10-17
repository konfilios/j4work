package org.j4work.kotlin.extjava.java.time

import org.j4work.kotlin.extjava.java.progression.ComparableProgression
import java.time.YearMonth

/**
 * Create an iterable progression of yearmonths.
 */
fun YearMonth.upTo(
    to: YearMonth,
    monthStep: Int = 1
) =
    ComparableProgression(this, to, YearMonthProgressionMonthStep(monthStep.toLong()))

