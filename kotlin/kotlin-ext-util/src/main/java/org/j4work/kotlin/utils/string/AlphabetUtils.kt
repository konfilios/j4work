package org.j4work.kotlin.utils.string

/**
 * .
 */
object AlphabetUtils {
    private val WHITESPACE = Regex("\\s")
    private val NONLETTER = Regex("(?U)[^\\p{Alpha}\\s]")
    private val LATIN_LETTER = Regex("[^a-zA-Z]")
    private val NONLATIN_LETTER = Regex("[a-zA-Z]")

    fun containsWordsWithMixedAlphabets(s: String) =
        s.replace(NONLETTER, "")     // Leave only letters and spaces in the name
            .split(WHITESPACE)          // Split into individual words
            .find { it ->
                // Find words which contain both latin and non-latin letters
                LATIN_LETTER.containsMatchIn(it)
                    && NONLATIN_LETTER.containsMatchIn(it)
            } != null                   // At least one word was found
}