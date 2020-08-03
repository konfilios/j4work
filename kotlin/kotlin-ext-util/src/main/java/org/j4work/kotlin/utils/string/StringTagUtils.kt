package org.j4work.kotlin.utils.string

import org.j4work.kotlin.extjava.java.lang.removeDiacritics

/**
 * .
 */
object StringTagUtils {
    private val REGEX_ONE_OR_MORE_WHITESPACE = Regex("\\s+")

    /**
     * Parse expression of the form "$data ($comment)".
     */
    fun parseCommentedData(s: String): Pair<String, String> =
        with(s.split('(', ')')) {
            when (size) {
                0 -> return Pair("", "")
                1 -> return Pair(this[0].trim(), "")
                else -> return Pair(this[0].trim(), this[1].trim())
            }
        }

    fun toTag(s: String) =
        s
            .removeDiacritics()                         // Remove diacritics
            .replace("'", "")                           // Remove single quotes (used as accents)
            .replace(REGEX_ONE_OR_MORE_WHITESPACE, " ") // Replace multiple whitespaces with a single space char
            .trim()                                     // Ignore surrounding whitespace
            .toLowerCase()                              // Lowercase

    /**
     * Split comma-separated string into list of tags.
     */
    fun toTagList(s: String, delimiter: String = ",") =
        s
            .split(delimiter).asSequence()
            .map { toTag(it) }
            .filter { !it.isEmpty() }
            .toList()
}