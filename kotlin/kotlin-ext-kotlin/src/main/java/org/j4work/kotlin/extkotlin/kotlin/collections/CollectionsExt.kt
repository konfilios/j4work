package org.j4work.kotlin.extkotlin.kotlin.collections

import java.math.BigDecimal

/**
 * Returns the sum of all values produced by [selector] function applied to each element in the collection.
 *
 * [BigDecimal] equivalent of [Iterable.sumByDouble].
 */
inline fun <T> Iterable<T>.sumByBigDecimal(selector: (T) -> BigDecimal): BigDecimal {
    var sum: BigDecimal = BigDecimal.ZERO
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

/**
 * Returns the sum of all BigDecimal values in the iterable.
 */
fun Iterable<BigDecimal>.sum(): BigDecimal {
    var sum: BigDecimal = BigDecimal.ZERO
    for (element in this) {
        sum += element
    }
    return sum
}