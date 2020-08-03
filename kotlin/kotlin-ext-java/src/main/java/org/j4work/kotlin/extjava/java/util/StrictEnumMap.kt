package org.j4work.kotlin.extjava.java.util

import java.util.*
import kotlin.reflect.KClass

/**
 * Immutable map whose keys are all the values of a given enum [K].
 */
class StrictEnumMap<K : Enum<K>, out V>(
    /**
     * Enum class from which we draw the keys.
     */
    keyType: KClass<K>,
    /**
     * Factory method creating a value of each key.
     */
    initValue: (K) -> V,
    /**
     * The internal map to which we delegate all operations.
     */
    private val enumMap: EnumMap<K, V> = createAndInitEnumMap(keyType.java, initValue)
) : StrictMap<K, V>, Map<K, V> by enumMap {

    /**
     * Strict maps always return non-null values.
     */
    override fun get(key: K): V {
        return enumMap[key]!!
    }

    companion object {
        /**
         * Create a map by iterating over all enum constants of [keyType].
         *
         * Internally, the optimized [EnumMap] offered by the java standard library
         * is used.
         */
        private fun <K : Enum<K>, V> createAndInitEnumMap(
            /**
             * The enum class whose enum constants are used.
             */
            keyType: Class<K>,
            /**
             * Factory method providing an initial value for every enum constant.
             */
            initValue: (K) -> V
        ) =
            EnumMap<K, V>(keyType).apply {
                keyType.enumConstants.forEach {
                    put(it, initValue(it))
                }
            }
    }
}

