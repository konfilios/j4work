package org.j4work.kotlin.utils.ranking

import org.j4work.kotlin.extjava.java.progression.ComparableProgressionDirection
import java.math.BigDecimal

class Ranking<out T> internal constructor(
    rankedItems: List<Ranked<T>>
) : Collection<Ranked<T>> by rankedItems {

}

fun <T> Collection<T>.rankBy(
    direction: ComparableProgressionDirection,
    valueSelector: (T) -> BigDecimal
): Ranking<T> {
    var prevValue: BigDecimal? = null
    var cumulativeValue = BigDecimal.ZERO
    var competitionRank = 0
    var ordinalRank = 0
    var denseRank = 0

    val comparator = when (direction) {
        ComparableProgressionDirection.ASC -> compareBy(valueSelector)
        ComparableProgressionDirection.DESC -> compareByDescending(valueSelector)
    }

    val sortedCollection = this.sortedWith(comparator)
    val rankedCollection = mutableListOf<Ranked<T>>()

    for (item in sortedCollection) {
        val value = valueSelector(item)

        // Update ranks and values
        ordinalRank++
        if (value != prevValue) {
            denseRank++
            competitionRank = ordinalRank
        }
        cumulativeValue += value
        prevValue = value

        // Add ranked item
        rankedCollection.add(Ranked(
            item = item,
            value = value,
            cumulativeValue = cumulativeValue,
            denseRank = denseRank,
            ordinalRank = ordinalRank,
            competitionRank = competitionRank
        ))
    }

    return Ranking(rankedCollection)
}
