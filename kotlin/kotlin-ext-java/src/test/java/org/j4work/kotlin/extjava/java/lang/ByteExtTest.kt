package org.j4work.kotlin.extjava.java.lang

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ByteExtTest {
    @Test
    fun toHexString() {
        val testPairs = mapOf(
            "00" to 0x00,
            "09" to 0x09,
            "0a" to 0x0a,
            "0f" to 0x0f,
            "10" to 0x10,
            "19" to 0x19,
            "1a" to 0x1a,
            "1f" to 0x1f,
            "90" to 0x90,
            "99" to 0x99,
            "9a" to 0x9a,
            "9f" to 0x9f,
            "a0" to 0xa0,
            "a9" to 0xa9,
            "aa" to 0xaa,
            "ff" to 0xff
        )

        for ((expectedHexString, actualIntValue) in testPairs) {
            assertThat(actualIntValue.toByte().toHexString())
                .isEqualTo(expectedHexString)
        }
    }

}