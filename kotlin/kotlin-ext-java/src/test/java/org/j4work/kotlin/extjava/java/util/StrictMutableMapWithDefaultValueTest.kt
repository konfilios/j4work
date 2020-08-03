package org.j4work.kotlin.extjava.java.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class StrictMutableMapWithDefaultValueTest {

    @Test
    fun `does not contain key but returns value`() {
        val map = StrictMutableMap.withDefaultValue<String, Int>(12)

        assertThat(map)
            .doesNotContainKey("b")

        assertThat(map["b"])
            .isEqualTo(12)
    }

    @Test
    fun `plusAssign returns default value and puts result back to map`() {
        val map = StrictMutableMap.withDefaultValue<String, Int>(12)

        map["b"] += 23

        assertThat(map)
            .containsEntry("b", 35)
    }
}