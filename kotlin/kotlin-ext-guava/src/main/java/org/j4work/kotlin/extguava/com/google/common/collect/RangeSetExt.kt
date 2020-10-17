package org.j4work.kotlin.extguava.com.google.common.collect

import com.google.common.collect.RangeSet

/**
 * Add all individual ranges that make up [o] into [this].
 */
fun <T : Comparable<T>> RangeSet<T>.add(o: RangeSet<T>) {
    o.asRanges().forEach { add(it) }
}
