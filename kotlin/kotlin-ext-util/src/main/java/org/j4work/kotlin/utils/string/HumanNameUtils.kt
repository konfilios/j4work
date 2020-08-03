package org.j4work.kotlin.utils.string

/**
 * Human name manipulation.
 */
object HumanNameUtils {
    val REGEX_LEADING_WHITESPACE = Regex("^\\s")
    val REGEX_TRAILING_WHITESPACE = Regex("\\s$")

    fun highlightSurroundingWhitespace(s : String) =
        s
            .replace(REGEX_LEADING_WHITESPACE, "\\$")
            .replace(REGEX_TRAILING_WHITESPACE, "\\$")

    /**
     * Sorts word contained in a string.
     */
    fun sortWords(s: String) =
        s.splitToSequence(' ')                  // Split
            .toSortedSet()                      // Sort
            .joinToString(separator = " ")      // Rejoin
}