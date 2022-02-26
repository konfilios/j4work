package org.j4work.kotlin.utils.string

import org.assertj.core.api.Assertions.assertThat
import org.j4work.kotlin.extguava.com.google.common.collect.TreeRangeSets
import org.j4work.kotlin.utils.string.HourRangeUtils.hourRangeSetToTimeRangeSet
import org.j4work.kotlin.utils.string.HourRangeUtils.isTimeRange
import org.j4work.kotlin.utils.string.HourRangeUtils.parseTimeRangeSet
import org.j4work.kotlin.utils.string.HourRangeUtils.timeRangeSetToHourRangeSet
import org.junit.jupiter.api.Test
import java.time.LocalTime

class HourRangeUtilsTest {
    @Test
    fun `is time range explicit 1am to explicit 6am`() {
        assertThat(isTimeRange("01-06"))
            .isTrue()
    }

    @Test
    fun `is time range explicit 0930am to explicit 0315pm`() {
        assertThat(isTimeRange("09.30-15.15"))
            .isTrue()
    }

    @Test
    fun `is time range explicit 10am to explicit 12pm and implicit 15pm to implicit 20pm`() {
        assertThat(isTimeRange("10-12/03-08"))
            .isTrue()
    }

    @Test
    fun `is time range explicit 10am to explicit 12pm and implicit 1515pm to implicit 2045pm`() {
        assertThat(isTimeRange("10-12/03.15-08.45"))
            .isTrue()
    }

    @Test
    fun `is time range test with spaces`() {
        assertThat(isTimeRange(" 10 - 01.15 / 05 - 08 "))
            .isTrue()
    }

    @Test
    fun `convert explicit 1am to explicit 6am`() {
        assertThat(timeRangeSetToHourRangeSet(TreeRangeSets.closedOpen(LocalTime.parse("01:00")..LocalTime.parse("06:00"))))
            .isEqualTo(TreeRangeSets.closedOpen(1..6))
    }

    @Test
    fun `convert explicit 10am to explicit 12pm and implicit 1515pm to implicit 2045pm`() {
        assertThat(timeRangeSetToHourRangeSet(TreeRangeSets.closedOpen(LocalTime.parse("10:00")..LocalTime.parse("12:00"),
            LocalTime.parse("15:15")..LocalTime.parse("20:45"))))
            .isEqualTo(TreeRangeSets.closedOpen(10..12, 15..20))
    }

    @Test
    fun `reverse convert explicit 1am to explicit 6am`() {
        assertThat(hourRangeSetToTimeRangeSet(TreeRangeSets.closedOpen(1..6)))
            .isEqualTo(TreeRangeSets.closedOpen(LocalTime.parse("01:00")..LocalTime.parse("06:00")))
    }

    @Test
    fun `reverse convert explicit 10am to explicit 12pm and implicit 1515pm to implicit 2045pm`() {
        assertThat(hourRangeSetToTimeRangeSet(TreeRangeSets.closedOpen(10..12, 15..20)))
            .isEqualTo(TreeRangeSets.closedOpen(LocalTime.parse("10:00")..LocalTime.parse("12:00"),
                LocalTime.parse("15:00")..LocalTime.parse("20:00")))
    }

    @Test
    fun `parse time range explicit 1am to explicit 6am`() {
        assertThat(parseTimeRangeSet("01-06"))
            .isEqualTo(TreeRangeSets.closedOpen(LocalTime.parse("01:00")..LocalTime.parse("06:00")))
    }

    @Test
    fun `parse time range implicit 1pm to implicit 6pm because of min start hour`() {
        assertThat(parseTimeRangeSet("01-06", 8))
            .isEqualTo(TreeRangeSets.closedOpen(LocalTime.parse("13:00")..LocalTime.parse("18:00")))
    }

    @Test
    fun `parse time range explicit 10am to implicit 6pm because end hour is smaller than start`() {
        assertThat(parseTimeRangeSet("10-06"))
            .isEqualTo(TreeRangeSets.closedOpen(LocalTime.parse("10:00")..LocalTime.parse("18:00")))
    }

    @Test
    fun `parse time range explicit 9am to explicit 3pm`() {
        assertThat(parseTimeRangeSet("09-15", 8))
            .isEqualTo(TreeRangeSets.closedOpen(LocalTime.parse("09:00")..LocalTime.parse("15:00")))
    }

    @Test
    fun `parse time range explicit 0930am to explicit 0315pm`() {
        assertThat(parseTimeRangeSet("09.30-15.15", 8))
            .isEqualTo(TreeRangeSets.closedOpen(LocalTime.parse("09:30")..LocalTime.parse("15:15")))
    }

    @Test
    fun `parse time range explicit 10am to explicit 12pm and implicit 15pm to implicit 20pm`() {
        assertThat(parseTimeRangeSet("10-12/03-08", 8))
            .isEqualTo(TreeRangeSets.closedOpen(LocalTime.parse("10:00")..LocalTime.parse("12:00"),
                LocalTime.parse("15:00")..LocalTime.parse("20:00")))
    }

    @Test
    fun `parse time range explicit 10am to explicit 12pm and implicit 1515pm to implicit 2045pm`() {
        assertThat(parseTimeRangeSet("10-12/03.15-08.45", 8))
            .isEqualTo(TreeRangeSets.closedOpen(LocalTime.parse("10:00")..LocalTime.parse("12:00"),
                LocalTime.parse("15:15")..LocalTime.parse("20:45")))
    }

    @Test
    fun `parse time range explicit 10am to implicit 13pm and implicit 17pm to implicit 20pm`() {
        assertThat(parseTimeRangeSet("10-01/05-08", 8))
            .isEqualTo(TreeRangeSets.closedOpen(LocalTime.parse("10:00")..LocalTime.parse("13:00"),
                LocalTime.parse("17:00")..LocalTime.parse("20:00")))
    }

    @Test
    fun `parse time range test with spaces`() {
        assertThat(parseTimeRangeSet(" 10 - 01.15 / 05 - 08 ", 8))
            .isEqualTo(TreeRangeSets.closedOpen(LocalTime.parse("10:00")..LocalTime.parse("13:15"),
                LocalTime.parse("17:00")..LocalTime.parse("20:00")))
    }
}