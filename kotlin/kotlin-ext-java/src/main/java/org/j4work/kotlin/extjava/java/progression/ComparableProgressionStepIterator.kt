package org.j4work.kotlin.extjava.java.progression

/**
 * An iterator utilizing a [ComparableProgressionStep].
 */
class ComparableProgressionStepIterator<T : Comparable<T>>(
    /**
     * The starting value of the progression.
     */
    startInclusive: T,
    /**
     * The end value of the progression.
     */
    private val endInclusive: T,
    /**
     * The step of the progression.
     */
    private val step: ComparableProgressionStep<T>
) : Iterator<T> {
    /**
     * What is the next element in the progression?
     */
    private var next: T = startInclusive

    /**
     * Is there another element in the progression?
     */
    private var hasNext: Boolean = !endHasBeenExceeded()

    /**
     * Check if [next] is still within iteration bounds
     */
    private fun endHasBeenExceeded() =
        step.direction.exceeds(next, endInclusive)

    override fun hasNext(): Boolean =
        hasNext

    override fun next(): T {
        // Use the already produced [next]
        val cur = next

        // Produce value for next iteration
        next = step.progress(next)

        if (endHasBeenExceeded()) {
            // Iteration will be over in the next call
            if (!hasNext) {
                // Iteration seems to have already ended
                throw kotlin.NoSuchElementException()
            }
            hasNext = false
        }

        return cur
    }
}