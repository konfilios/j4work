package org.j4work.kotlin.utils.greek

/**
 * Utilities associated with greek writing of calendar information.
 */
object GreekCalendar {

    /**
     * Calculate month number from full (unaccented) month name.
     *
     * todo: Strip accents from input [monthName]
     */
    fun getMonthNumberFromFullName(monthName: String) =
        when (monthName) {
            "ιανουαριος"  -> 1
            "φεβρουαριος" -> 2
            "μαρτιος"     -> 3
            "απριλιος"    -> 4
            "μαιος"       -> 5
            "ιουνιος"     -> 6
            "ιουλιος"     -> 7
            "αυγουστος"   -> 8
            "σεπτεμβριος" -> 9
            "οκτωβριος"   -> 10
            "νοεμβριος"   -> 11
            "δεκεμβριος"  -> 12
            else          -> throw Exception("Unrecognized month name '$monthName'")
        }
}