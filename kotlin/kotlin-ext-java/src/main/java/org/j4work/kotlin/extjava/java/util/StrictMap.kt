package org.j4work.kotlin.extjava.java.util

/**
 * Strict maps are Maps which always return non-null values.
 */
interface StrictMap<K, out V> : Map<K, V> {
    /**
     * Return a non-null value for given key.
     */
    override fun get(key: K): V
}