package org.j4work.kotlin.utils.string

import org.assertj.core.api.Assertions.assertThat
import org.j4work.kotlin.utils.string.StringTagUtils.parseCommentedData
import org.junit.jupiter.api.Test

class StringTagUtilsTest {

    @Test
    fun `parse no data and no comments`() {
        assertThat(parseCommentedData(""))
            .isEqualTo(Pair("", ""))
    }

    @Test
    fun `parse whitespace`() {
        assertThat(parseCommentedData("  "))
            .isEqualTo(Pair("", ""))
    }

    @Test
    fun `parse data without comment`() {
        assertThat(parseCommentedData("the data"))
            .isEqualTo(Pair("the data", ""))
    }

    @Test
    fun `parse data with whitespace without comment`() {
        assertThat(parseCommentedData(" the data "))
            .isEqualTo(Pair("the data", ""))
    }

    @Test
    fun `parse only comment`() {
        assertThat(parseCommentedData("(the comm)"))
            .isEqualTo(Pair("", "the comm"))
    }

    @Test
    fun `parse only comment with whitespace`() {
        assertThat(parseCommentedData(" ( the comm ) "))
            .isEqualTo(Pair("", "the comm"))
    }

    @Test
    fun `parse data with comment but without space`() {
        assertThat(parseCommentedData("the data(the comm)"))
            .isEqualTo(Pair("the data", "the comm"))
    }

    @Test
    fun `parse data with comment and whitespace`() {
        assertThat(parseCommentedData(" the data ( the comm ) "))
            .isEqualTo(Pair("the data", "the comm"))
    }
}