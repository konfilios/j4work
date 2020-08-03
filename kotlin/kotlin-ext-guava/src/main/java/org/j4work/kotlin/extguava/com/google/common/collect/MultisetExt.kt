package org.j4work.kotlin.extguava.com.google.common.collect

import com.google.common.collect.Multiset

/**
 * Add all elements of multiset [o] to [this].
 */
fun <E> Multiset<E>.add(o: Multiset<E>) {
    o.entrySet().forEach { add(it.element, it.count) }
}
