package org.j4work.kotlin.utils.string

import org.j4work.kotlin.extguava.com.google.common.collect.TreeRangeSets
import org.j4work.kotlin.utils.string.HourRangeUtils.parseHourRangeSet
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * .
 */
class HourRangeUtilsTest {

    @Test
    fun `explicit 1am to explicit 6am`() {
        assertThat(parseHourRangeSet("01-06"))
            .isEqualTo(TreeRangeSets.closedOpen(1..6))
    }

    @Test
    fun `implicit 1pm to implicit 6pm because of min start hour`() {
        assertThat(parseHourRangeSet("01-06", 8))
            .isEqualTo(TreeRangeSets.closedOpen(13..18))
    }

    @Test
    fun `explicit 10am to implicit 6pm because end hour is smaller than start`() {
        assertThat(parseHourRangeSet("10-06"))
            .isEqualTo(TreeRangeSets.closedOpen(10..18))
    }

    @Test
    fun `explicit 9am to explicit 3pm`() {
        assertThat(parseHourRangeSet("09-15", 8))
            .isEqualTo(TreeRangeSets.closedOpen(9..15))
    }

    @Test
    fun `explicit 10am to explicit 12pm and implicit 15pm to implicit 20pm`() {
        assertThat(parseHourRangeSet("10-12/03-08", 8))
            .isEqualTo(TreeRangeSets.closedOpen(10..12, 15..20))
    }

    @Test
    fun `explicit 10am to implicit 13pm and implicit 17pm to implicit 20pm`() {
        assertThat(parseHourRangeSet("10-01/05-08", 8))
            .isEqualTo(TreeRangeSets.closedOpen(10..13, 17..20))
    }

    @Test
    fun `test with spaces`() {
        assertThat(parseHourRangeSet(" 10 - 01 / 05 - 08 ", 8))
            .isEqualTo(TreeRangeSets.closedOpen(10..13, 17..20))
    }
}