package org.j4work.kotlin.extjava.java.progression

enum class ComparableProgressionDirection {
    /**
     * Ascending (up) direction
     */
    ASC {
        override fun toString() = "ascending"

        override fun opposite() =
            DESC

        override fun <T : Comparable<T>> exceeds(value: T, limit: T) =
            value > limit
    },

    /**
     * Descending (down) direction.
     */
    DESC {
        override fun toString() = "descending"

        override fun opposite() =
            ASC

        override fun <T : Comparable<T>> exceeds(value: T, limit: T) =
            value < limit
    };

    /**
     * Return opposite direction
     */
    abstract fun opposite() : ComparableProgressionDirection

    /**
     * Does value exceed limit in the direction we are pointing?
     */
    abstract fun <T : Comparable<T>> exceeds(value: T, limit: T): Boolean

    companion object {
        fun ofStep(step: Int) =
            if (step > 0) {
                ASC
            } else if (step < 0) {
                DESC
            } else {
                throw IllegalArgumentException("A step of '0' does produces neither ascending nor descending order")
            }
        fun ofStep(step: Long) =
            if (step > 0) {
                ASC
            } else if (step < 0) {
                DESC
            } else {
                throw IllegalArgumentException("A step of '0' does produces neither ascending nor descending order")
            }
    }
}