package org.j4work.kotlin.utils.rfm

import org.apache.commons.math3.stat.descriptive.SummaryStatistics

internal class CategoryBuilder(
    val categoryOrd: Int,
    val firstItemOrd: Int,
    val expectedLastItemOrd: Int
) {
    var lastItemOrd: Int = 0
    val summaryStats = SummaryStatistics()

    fun addItem(itemOrd: Int, value: Int) {
        lastItemOrd = itemOrd

        summaryStats.addValue(value.toDouble())
    }

}