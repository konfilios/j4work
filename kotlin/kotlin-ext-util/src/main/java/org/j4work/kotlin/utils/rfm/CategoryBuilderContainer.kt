package org.j4work.kotlin.utils.rfm

import org.apache.commons.math3.stat.descriptive.StatisticalSummary

internal class CategoryBuilderContainer(
    val itemCount: Int,
    val categoryCount: Int
) {
    val categoriesStats = mutableMapOf<Int, StatisticalSummary>()

    fun createFirstCategoryBuilder() =
        createCategoryBuilder(
            categoryOrd = 1,
            firstItemOrd = 0
        )

    fun commitCategoryBuilder(categoryBuilder: CategoryBuilder): CategoryBuilder {
        if (categoryBuilder.summaryStats.n > 0) {
            finalizeCat(categoryBuilder)

            if (categoryBuilder.categoryOrd < categoryCount) {
                return createCategoryBuilder(
                    categoryOrd = categoryBuilder.categoryOrd + 1,
                    firstItemOrd = categoryBuilder.lastItemOrd + 1
                )
            }
        }

        return categoryBuilder
    }

    private fun createCategoryBuilder(categoryOrd: Int, firstItemOrd: Int): CategoryBuilder {
        return CategoryBuilder(
            categoryOrd = categoryOrd,
            firstItemOrd = firstItemOrd,
            expectedLastItemOrd = firstItemOrd - 1 +
                (itemCount - firstItemOrd + 1) / (categoryCount - categoryOrd + 1)
        )
    }

    private fun finalizeCat(categoryBuilder: CategoryBuilder) {
        categoriesStats[categoryBuilder.categoryOrd] = categoryBuilder.summaryStats.summary
    }

}