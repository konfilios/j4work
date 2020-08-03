package org.j4work.kotlin.extguava.kotlin.ranges

import com.google.common.collect.Range

/**
 * Convert a kotlin [ClosedRange] to a guava [Range]
 */
fun <K : Comparable<K>> ClosedRange<K>.toGuavaRange(): Range<K> =
    Range.closed(this.start, this.endInclusive)