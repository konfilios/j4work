package org.j4work.kotlin.extjava.java.lang

import java.text.Normalizer

private val WHITESPACE = Regex("\\s")
private val REGEX_MARKS = Regex("\\p{M}")

fun String.removeDiacritics() =
    Normalizer.normalize(this, Normalizer.Form.NFD) // Decompose letters from diacritics
        .replace(REGEX_MARKS, "")                   // Remove decomposed marks

fun String.removeAllWhitespace() =
    replace(WHITESPACE, "")