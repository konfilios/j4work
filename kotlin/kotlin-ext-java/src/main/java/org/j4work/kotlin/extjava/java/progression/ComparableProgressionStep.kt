package org.j4work.kotlin.extjava.java.progression

/**
 * Implementation of a step up or down for a generic [ComparableProgression].
 */
interface ComparableProgressionStep<T : Comparable<T>> {
    /**
     * Progress given value to the next step.
     */
    fun progress(value: T): T

    /**
     * Create a new progression step with the reverse direction.
     */
    fun reverse(): ComparableProgressionStep<T>

    /**
     * Direction of step.
     *
     * If direction is ASC, then every call to progress() method will produce a new
     * value which is greater than the previous based on their natural comparator
     * Conversely, if direction is DESC, progress will always produce smaller values.
     */
    val direction: ComparableProgressionDirection
}