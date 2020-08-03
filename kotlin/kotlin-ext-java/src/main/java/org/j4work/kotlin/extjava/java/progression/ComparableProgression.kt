package org.j4work.kotlin.extjava.java.progression

/**
 * An iterable Progression of [Comparable] objects.
 */
class ComparableProgression<T : Comparable<T>>(
    /**
     * The starting value of the progression.
     */
    val startInclusive: T,
    /**
     * The end value of the progression.
     */
    val endInclusive: T,
    /**
     * The step of the progression.
     */
    val step: ComparableProgressionStep<T>
) : Iterable<T> {

    override fun iterator() =
        ComparableProgressionStepIterator(startInclusive, endInclusive, step)

    override fun equals(other: Any?): Boolean =
        other is ComparableProgression<*> &&
            startInclusive == other.startInclusive &&
            endInclusive == other.endInclusive &&
            step == other.step

    override fun hashCode() : Int {
        val start = startInclusive.hashCode()
        val end = endInclusive.hashCode()
        val step = step.hashCode()
        return (31 * (31 * (start xor (start ushr 32)) + (end xor (end ushr 32))) + (step xor (step ushr 32)))
    }

    override fun toString() =
        "$startInclusive ${step.direction} to $endInclusive with step $step"
}