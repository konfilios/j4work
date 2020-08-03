package org.j4work.kotlin.utils.greek

import org.j4work.kotlin.extjava.java.lang.removeDiacritics

/**
 * Convert greek word to greeklish.
 */
object GreeklishUtils {
    private val GREEKLISH_MAP_DOUBLE_LETTERS = mapOf(
        'α' to mapOf(
            'ι' to "e",
            'υ' to "av"
        ),
        'β' to mapOf(
            'β' to "v"
        ),
        'γ' to mapOf(
            'γ' to "g",
            'κ' to "g"
        ),
        'δ' to mapOf(
            'δ' to "d"
        ),
        'ε' to mapOf(
            'ι' to "i",
            'υ' to "ev"
        ),
        'κ' to mapOf(
            'κ' to "k"
        ),
        'λ' to mapOf(
            'λ' to "l"
        ),
        'μ' to mapOf(
            'μ' to "m"
        ),
        'ν' to mapOf(
            'ν' to "n"
        ),
        'ο' to mapOf(
            'ι' to "i",
            'υ' to "u"
        ),
        'π' to mapOf(
            'π' to "p"
        ),
        'ρ' to mapOf(
            'ρ' to "r"
        ),
        'σ' to mapOf(
            'σ' to "s"
        ),
        'τ' to mapOf(
            'τ' to "t"
        ),
        'υ' to mapOf(
            'ι' to "i"
        )
    )

    private val GREEKLISH_MAP_SINGLE_LETTERS = mapOf(
        'α' to "a",
        'β' to "v",
        'γ' to "g",
        'δ' to "d",
        'ε' to "e",
        'ζ' to "z",
        'η' to "i",
        'θ' to "th",
        'ι' to "i",
        'κ' to "k",
        'λ' to "l",
        'μ' to "m",
        'ν' to "n",
        'ξ' to "x",
        'ο' to "o",
        'π' to "p",
        'ρ' to "r",
        'σ' to "s",
        'τ' to "t",
        'υ' to "i",
        'φ' to "f",
        'χ' to "h",
        'ψ' to "ps",
        'ω' to "o",
        'ς' to "s"
    )

    /**
     * http://www.greek-language.gr/greekLang/files/document/modern_greek/grammatiki.triantafyllidi.pdf.
     */
    fun toGreeklish(s : String): String {
        val sWithoutDiacritics = s.removeDiacritics()
        val sb = StringBuffer(sWithoutDiacritics.length)

        var i = 0;
        while (i < sWithoutDiacritics.length) {
            i += transformToGreeklish(
                sb,
                sWithoutDiacritics,
                i)
        }

        return sb.toString()
    }

    /**
     * Transforms characters form source string and appends them
     * to the destination string buffer.
     *
     * @return Number of source string characters consumed.
     */
    private fun transformToGreeklish(
        dstStringBuffer: StringBuffer,
        srcString: String,
        srcOffset: Int
    ): Int {
        val firstSrcChar = srcString[srcOffset].toLowerCase()

        // Avoid OutOfBounds exception
        val j = srcOffset + 1
        if (j < srcString.length) {
            val secondSrcChar = srcString[j].toLowerCase()
            val transformedString = GREEKLISH_MAP_DOUBLE_LETTERS
                .get(firstSrcChar)
                ?.get(secondSrcChar)

            if (transformedString != null) {
                dstStringBuffer.append(transformedString)
                // We consumed two characters of the source string
                return 2
            }
        }

        // Single mapped letter or the source character itself
        // We only consume one character from the source string
        dstStringBuffer.append(GREEKLISH_MAP_SINGLE_LETTERS[firstSrcChar] ?: firstSrcChar)
        return 1
    }
}