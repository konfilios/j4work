package org.j4work.kotlin.utils.rfm

import org.apache.commons.math3.stat.descriptive.StatisticalSummary
import org.j4work.kotlin.extjava.java.progression.ComparableProgressionDirection

class RfmScoreboard<K, T>(
    val scores: Map<K, RfmScore<T>>,
    val recencyStats: Map<Int, StatisticalSummary>,
    val frequencyStats: Map<Int, StatisticalSummary>,
    val monetaryStats: Map<Int, StatisticalSummary>
) {

}

fun <K, T>
    Sequence<T>.calculateRfmScores(
    recencyCategoryCount: Int = 5,
    frequencyCategoryCount: Int = 5,
    monetaryCategoryCount: Int = 5,
    keySelector: (T) -> K,
    recencySelector: (T) -> Int,
    frequencySelector: (T) -> Int,
    monetarySelector: (T) -> Int

): RfmScoreboard<K, T> {
    val scores = associateBy(
        keySelector,
        { MutableRfmScoreImpl(
            item = it,
            recencyValue = recencySelector(it),
            frequencyValue = frequencySelector(it),
            monetaryValue = monetarySelector(it)
        ) }
    )
        val recencyCategories = scores.values.calculateCategories(
            categoryCount = recencyCategoryCount,
            valueSelector = { it.recencyValue },
            categoryCallback = { rfm, category -> rfm.recencyCategory = category }
        )

    val frequencyCategories = scores.values.calculateCategories(
            categoryCount = frequencyCategoryCount,
            valueSelector = { it.frequencyValue },
            categoryCallback = { rfm, category -> rfm.frequencyCategory = category }
        )

        val monetaryCategories = scores.values.calculateCategories(
            categoryCount = monetaryCategoryCount,
            valueSelector = { it.monetaryValue },
            categoryCallback = { rfm, category -> rfm.monetaryCategory = category }
        )


    return RfmScoreboard(
        scores = scores,
        recencyStats = recencyCategories,
        frequencyStats = frequencyCategories,
        monetaryStats = monetaryCategories
    )
}

private fun <F> Collection<F>.calculateCategories(
    categoryCount: Int,
    valueSelector: (F) -> Int,
    sortDirection: ComparableProgressionDirection = ComparableProgressionDirection.ASC,
    categoryCallback: (F, Int) -> Unit
) : Map<Int, StatisticalSummary> {
    val sorted = when (sortDirection) {
        ComparableProgressionDirection.ASC  -> sortedBy(valueSelector)
        ComparableProgressionDirection.DESC -> sortedByDescending(valueSelector)
    }

    val curCatContainer = CategoryBuilderContainer(
        itemCount = sorted.size,
        categoryCount = categoryCount
    )
    var curCat = curCatContainer.createFirstCategoryBuilder()

    var id = 0
    var prevValue : Int? = null
    sorted.forEach {
        categoryCallback(it, curCat.categoryOrd)

        val curValue = valueSelector(it)
        curCat.addItem(id, curValue)

        if (id >= curCat.expectedLastItemOrd
            && prevValue != curValue
            ) {
            // Finalize previous category and start new
            curCat = curCatContainer.commitCategoryBuilder(curCat)
        }

        id++
        prevValue = curValue
    }
    // Commit last category
    curCatContainer.commitCategoryBuilder(curCat)

    return curCatContainer.categoriesStats
}

