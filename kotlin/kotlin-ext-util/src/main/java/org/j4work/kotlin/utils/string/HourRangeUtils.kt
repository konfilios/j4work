package org.j4work.kotlin.utils.string

import com.google.common.collect.BoundType
import com.google.common.collect.Range
import com.google.common.collect.RangeSet
import com.google.common.collect.TreeRangeSet

/**
 * Convert hour range strings to [RangeSet] objects.
 */
object HourRangeUtils {
    private val hourRangeSymbols = Regex("\\d[-/\\d\\s]+")

    /**
     * Is given string a parseable hour range?
     */
    fun isHourRange(s: String) =
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
    fun parseHourRangeSet(
        rangeSetString: String,
        infimumStartHour: Int = 0
    ): RangeSet<Int> {
        val rangeSet = TreeRangeSet.create<Int>()
        var minimumStartHour = infimumStartHour

        for (rangeString in rangeSetString.split("/")) {
            val (startString, endString) =
                rangeString.split("-").takeIf { it.size >= 2 }
                    ?: throw Exception("Could not parse time range $rangeString")

            // Parse start hour and add 12 hours if it's below threshold
            val start = parseStartHour(startString, minimumStartHour, rangeSetString)

            // Parse end hour and 12 hours if it's below start hour
            val end = parseEndHour(endString, start, rangeSetString)

            // Create range and add it to the set
            rangeSet.add(Range.closedOpen(start, end))

            // Update minimum start hour so next range is properly evaluated
            minimumStartHour = end
        }

        return rangeSet
    }

    private fun parseEndHour(
        endString: String,
        minValue: Int,
        rangeSetString: String
    ): Int {
        val end = endString.trim().toInt().let {
            if (it < minValue) it + 12 else it
        }
        require(end in 0..23, {
            "Could not parse end hour '$endString' in '$rangeSetString'"
        })
        return end
    }

    private fun parseStartHour(
        startString: String,
        minValue: Int,
        rangeSetString: String
    ): Int {
        val start = startString.trim().toInt().let {
            if (it < minValue) it + 12 else it
        }
        require(start in 0..23, {
            "Could not parse start hour '$startString' in '$rangeSetString'"
        })
        return start
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
            BoundType.OPEN   -> 1
            BoundType.CLOSED -> 0
        }
}