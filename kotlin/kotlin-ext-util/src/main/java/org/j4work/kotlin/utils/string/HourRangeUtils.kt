package org.j4work.kotlin.utils.string

import com.google.common.collect.BoundType
import com.google.common.collect.Range
import com.google.common.collect.RangeSet
import com.google.common.collect.TreeRangeSet
import java.time.LocalTime

/**
 * Convert hour range strings to [RangeSet] objects.
 */
object HourRangeUtils {
    private val timeRegex = "\\d+(\\.\\d+)?"
    private val timeWithSpacesRegex = "\\s*${timeRegex}\\s*"
    private val hourRangeSymbols = Regex("(/?${timeWithSpacesRegex}-${timeWithSpacesRegex})+")

    /**
     * Is given string a parseable hour range?
     */
    fun isTimeRange(s: String) =
        hourRangeSymbols.matches(s)

    /**
     * Parse hour range string into a [RangeSet].
     *
     * Note that if the [infimumStartHour] is set to
     * a non-zero value, then any hour number found in the
     * [rangeSetString] which is less than the [infimumStartHour]
     * will be implicitly advanced from a.m. to p.m. (see unit
     * tests for examples)
     *
     * @param rangeSetString The string to parse
     * @param infimumStartHour Infimum start hour for am/pm implicit conversion
     */
    fun parseTimeRangeSet(
        rangeSetString: String,
        infimumStartHour: Int = 0
    ): TreeRangeSet<LocalTime> {
        var minimumStartHour = infimumStartHour
        val timeRangeSet = TreeRangeSet.create<LocalTime>()

        for (rangeString in rangeSetString.split("/")) {
            val (startString, endString) =
                rangeString.split("-").takeIf { it.size >= 2 }
                    ?: throw Exception("Could not parse time range $rangeString")

            // Parse start hour and add 12 hours if it's below threshold
            val startTime = parseStartHour(startString, minimumStartHour, rangeSetString)

            // Parse end hour and 12 hours if it's below start hour
            val endTime = parseEndHour(endString, startTime.hour, rangeSetString)

            timeRangeSet.add(Range.closedOpen(startTime, endTime))

            // Update minimum start hour so next range is properly evaluated
            minimumStartHour = endTime.hour
        }

        return timeRangeSet
    }

    fun timeRangeSetToHourRangeSet(timeRangeSet: RangeSet<LocalTime>): TreeRangeSet<Int> {
        val hourRangeSet = TreeRangeSet.create<Int>()
        for (timeRange in timeRangeSet.asRanges()) {
            // Create range and add it to the set
            hourRangeSet.add(Range.closedOpen(timeRange.lowerEndpoint().hour, timeRange.upperEndpoint().hour))
        }
        return hourRangeSet
    }

    fun hourRangeSetToTimeRangeSet(hourRangeSet: RangeSet<Int>): TreeRangeSet<LocalTime> {
        val timeRangeSet = TreeRangeSet.create<LocalTime>()
        for (hourRange in hourRangeSet.asRanges()) {
            // Create range and add it to the set
            timeRangeSet.add(Range.closedOpen(LocalTime.of(hourRange.lowerEndpoint(), 0),
                LocalTime.of(hourRange.upperEndpoint(), 0)))
        }
        return timeRangeSet
    }

    private fun parseEndHour(
        endString: String,
        minValue: Int,
        rangeSetString: String
    ): LocalTime {
        val (endHourInt: Int, endMinuteInt: Int) = parseTime(endString)

        val endHour = endHourInt.let {
            if (it < minValue) it + 12 else it
        }
        require(endHour in 0..23, {
            "Could not parse end hour '$endString' in '$rangeSetString'"
        })
        return LocalTime.of(endHour, endMinuteInt)
    }

    private fun parseStartHour(
        startString: String,
        minHour: Int,
        rangeSetString: String
    ): LocalTime {
        val (startHourInt: Int, startMinuteInt: Int) = parseTime(startString)

        val startHour = startHourInt.let {
            if (it < minHour) it + 12 else it
        }
        require(startHour in 0..23, {
            "Could not parse start hour '$startString' in '$rangeSetString'"
        })
        return LocalTime.of(startHour, startMinuteInt)
    }

    private fun parseTime(timeString: String): Pair<Int, Int> {
        val timeElements = timeString.trim().split(".")

        return if (timeElements.size > 1) {
            Pair(timeElements[0].toInt(), timeElements[1].toInt())
        } else if (timeElements.isNotEmpty()) {
            Pair(timeElements[0].toInt(), 0)
        } else {
            throw IllegalArgumentException("Could not parse '$timeString' into hours and minutes")
        }
    }

    fun measure(rangeSet: RangeSet<Int>) =
        rangeSet.asRanges().sumBy { measure(it) }

    fun measure(range: Range<Int>) =
        1 +
            range.upperEndpoint() -
            range.lowerEndpoint() -
            nonMeasurable(range.upperBoundType()) -
            nonMeasurable(range.lowerBoundType())

    fun nonMeasurable(boundType: BoundType) =
        when (boundType) {
            BoundType.OPEN -> 1
            BoundType.CLOSED -> 0
        }
}