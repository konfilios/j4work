package org.j4work.kotlin.extjava.java.lang


/**
 * kotlin.Byte extensions
 */

private const val HEX_CHARS = "0123456789abcdef"

/**
 * Two-character hex string representation of byte.
 *
 * Each byte (8 bites) contains 2 nibbles (4 bits), the left and the right:
 *
 * byte = [LLLL RRRR]
 * hex  =    l   r
 */
fun Byte.toHexString(): String {
    // Bitwise operations are only possible for Int and Long in kotlin
    val octet = toInt()

    val leftNibble = HEX_CHARS[(octet and 0b1111_0000).ushr(4)]
    val rightNibble = HEX_CHARS[octet and 0b0000_1111]

    return "$leftNibble$rightNibble"
}
