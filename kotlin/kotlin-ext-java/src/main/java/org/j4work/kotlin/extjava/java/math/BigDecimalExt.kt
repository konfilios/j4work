package org.j4work.kotlin.extjava.java.math

import java.math.BigDecimal
import java.math.RoundingMode

private val BIG_DECIMAL_HUNDRED = BigDecimal.valueOf(100)

val BigDecimal.HUNDRED: BigDecimal
    get() = BIG_DECIMAL_HUNDRED

/**
 * Multiplication with an integer requires prior conversion to BigDecimal.
 */
operator fun BigDecimal.times(o: Int) =
    this.times(BigDecimal.valueOf(o.toLong()))

fun BigDecimal.safePercentageOver(
    denominator: BigDecimal,
    scale: Int = 2,
    roundingMode: RoundingMode = RoundingMode.HALF_UP,
    valueIfDevisionByZero: BigDecimal = BigDecimal.valueOf(0, scale)
) =
    if (denominator.signum() == 0) {
        valueIfDevisionByZero
    } else {
        (BIG_DECIMAL_HUNDRED * this).divide(denominator, scale, roundingMode)
    }

/**
 * Convert nullable double to BigDecimal.
 */
fun Double?.toBigDecimal(
    scale: Int,
    defaultIfNull: BigDecimal = BigDecimal.ZERO,
    roundingMode: RoundingMode = RoundingMode.HALF_UP
): BigDecimal =
    if (this == null) {
        defaultIfNull
    } else {
        try {
            BigDecimal(this)
                .setScale(scale, roundingMode)
        } catch (e: IllegalStateException) {
            throw IllegalStateException("Could not convert double '$this' to BigDecimal", e)
        }
    }

/**
 * Convert nullable string to BigDecimal.
 */
fun String?.toBigDecimal(
    scale: Int,
    defaultIfNull: BigDecimal = BigDecimal.ZERO,
    roundingMode: RoundingMode = RoundingMode.HALF_UP
): BigDecimal =
    if (this.isNullOrBlank()) {
        defaultIfNull
    } else {
        try {
            BigDecimal(this)
                .setScale(scale, roundingMode)
        } catch (e: Exception) {
            throw IllegalStateException("Could not convert string '$this' to BigDecimal", e)
        }
    }
