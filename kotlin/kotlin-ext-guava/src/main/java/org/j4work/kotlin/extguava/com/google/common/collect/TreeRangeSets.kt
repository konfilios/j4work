package org.j4work.kotlin.extguava.com.google.common.collect

import com.google.common.collect.Range
import com.google.common.collect.TreeRangeSet

/**
 * Factory methods for [TreeRangeSet] instances.
 */
object TreeRangeSets {

    /**
     * Create a [TreeRangeSet] of the form [a, b).
     */
    fun <T : Comparable<T>> closedOpen(vararg ranges: ClosedRange<T>): TreeRangeSet<T> =
        TreeRangeSet.create<T>().apply {
            ranges.forEach {
                add(Range.closedOpen(it.start, it.endInclusive))
            }
        }
}
