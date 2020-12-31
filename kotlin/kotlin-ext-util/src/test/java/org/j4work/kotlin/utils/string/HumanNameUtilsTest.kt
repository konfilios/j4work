package org.j4work.kotlin.utils.string

import org.assertj.core.api.Assertions.assertThat
import org.j4work.kotlin.utils.string.HumanNameUtils.sortWords
import org.junit.jupiter.api.Test

class HumanNameUtilsTest {

    @Test
    fun `sorted must stay the same`() {
        assertThat(sortWords("Ab Yz"))
            .isEqualTo("Ab Yz")
    }

    @Test
    fun `unsorted must be sorted`() {
        assertThat(sortWords("Yz Ab"))
            .isEqualTo("Ab Yz")
    }
}