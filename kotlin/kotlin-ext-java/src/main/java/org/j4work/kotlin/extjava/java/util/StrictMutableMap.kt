package org.j4work.kotlin.extjava.java.util

/**
 * Mutable map which always returns a non-null value for a key.
 */
interface StrictMutableMap<K, V> : MutableMap<K, V> {
    /**
     * Always get a non-null values.
     */
    override fun get(key: K): V

    /**
     * Always set a non-null values.
     */
    operator fun set(key: K, value: V) = put(key, value)

    /**
     * StrictMutableMap implementation which returns a default value for every key that is not set.
     */
    private class StrictMutableMapWithDefaultValue<K, V>(
        val defaultValue: V,
        val map: MutableMap<K, V> = mutableMapOf()
    ) : StrictMutableMap<K, V>,
        /**
         * Implement [MutableMap] by delegation to [map].
         */
        MutableMap<K, V> by map {

        /**
         * Return [defaultValue] if no value is found for [key].
         *
         * Note that the returned value is not [set] after it is
         * returned. You always need to [set] a value manually,
         * otherwise you will always get the default.
         */
        override fun get(key: K) =
            map.getOrDefault(key, defaultValue)
    }

    companion object {
        /**
         * Factory method for strict maps that return a default value when a key is not set.
         */
        fun <K, V> withDefaultValue(defaultValue: V): StrictMutableMap<K, V> {
            return StrictMutableMapWithDefaultValue(defaultValue)
        }
    }
}