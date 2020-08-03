package org.j4work.kotlin.utils.ranking

import java.math.BigDecimal

class Ranked<out T>(
    val item: T,

    val value: BigDecimal,
    val cumulativeValue: BigDecimal,

    val denseRank: Int,
    val ordinalRank: Int,
    val competitionRank: Int
)