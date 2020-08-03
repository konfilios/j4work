package org.j4work.kotlin.utils.rfm

import org.apache.commons.math3.stat.descriptive.StatisticalSummary
import java.lang.Math.abs

object RfmScoreboardPrinter {
    private val SEPARATOR = "-----------------"

    fun <K, V> printEverything(rfm: RfmScoreboard<K, V>) {
        printAllIndividualScores(rfm)
        printAllCardinalities(rfm)
    }

    fun <K, V> printAllCardinalities(rfm: RfmScoreboard<K, V>) {
        printCardinalities("Recencies", rfm.recencyStats, rfm.scores.size)
        printCardinalities("Frequencies", rfm.frequencyStats, rfm.scores.size)
        printCardinalities("Monetaries", rfm.monetaryStats, rfm.scores.size)
    }

    fun <K, V> printAllIndividualScores(rfm: RfmScoreboard<K, V>) {
        System.out.println("Name        ")
        System.out.println(SEPARATOR)
        System.out.println("%25s  %s".format("Name", "R-F-M"))
        rfm.scores.entries.forEach { (key, score) ->
            System.out.println("%25.25s: %s".format(
                key.toString(),
                score.totalScore()
            ))
        }
    }

    private fun printCardinalities(
        title: String,
        stats: Map<Int, StatisticalSummary>,
        totalPopulation : Int
    ) {
        System.out.println()
        System.out.println(title)
        System.out.println(SEPARATOR)
        System.out.println("%7s %13s %15s".format(
            "Category",
            "Frequency",
            "Mean"
        ))
        System.out.println("%7s %5s %7s".format(
            "#",
            "Abs.",
            "Rel."
        ))
        stats.forEach { category, statSummary ->
            System.out.println("%7d %5d %6.1f%% %8.1f ± %4.1f (%5.1f%%)  [%5d, %5d] = %4d".format(
                category,
                statSummary.n,
                (100.0 * statSummary.n) / totalPopulation,
                statSummary.mean,
                statSummary.standardDeviation,
                (100.0 * statSummary.standardDeviation) / abs(statSummary.mean),
                statSummary.min.toInt(),
                statSummary.max.toInt(),
                statSummary.max.toInt() - statSummary.min.toInt()
            ))
        }

    }

}