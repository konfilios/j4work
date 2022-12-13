package org.j4work.kotlin.extpoi.org.apache.poi.ss.usermodel

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.ss.util.CellReference
import org.apache.poi.ss.util.NumberToTextConverter
import org.j4work.kotlin.extjava.java.math.toBigDecimal
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

/**
 * Return as string independently of cell type.
 */
val Cell.asString: String?
    get() = when (cellType) {
        CellType.NUMERIC -> NumberToTextConverter.toText(numericCellValue)
        else             -> stringCellValue
    }

/**
 * Convert dateCellValue (Date) to a UTC local date.
 */
val Cell.asLocalDate
    get() = try {
        dateCellValue?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
    } catch (e: IllegalStateException) {
        throw IllegalStateException("$ref: Could not convert to LocalDate", e)
    }

val Cell.ref: String
    get() = CellReference(sheet.sheetName, rowIndex, columnIndex, false, false).formatAsString()

fun Cell?.asString(defaultIfNull: String = ""): String =
    if (this == null) {
        defaultIfNull
    } else {
        when (cellType) {
            CellType.NUMERIC -> NumberToTextConverter.toText(numericCellValue)
            else             -> stringCellValue
        }
    }

/**
 * Convert numericCellValue (double) to BigDecimal.
 */
fun Cell?.asBigDecimal(
    scale: Int,
    defaultIfNull: BigDecimal = BigDecimal.ZERO,
    roundingMode: RoundingMode = RoundingMode.HALF_UP
): BigDecimal =
    if (this == null) {
        defaultIfNull
    } else {
        try {
            when (this.cellType) {
                CellType.FORMULA -> {
                    when (this.cachedFormulaResultType) {
                        CellType.NUMERIC -> this.numericCellValue.toBigDecimal(scale, defaultIfNull, roundingMode)
                        else -> this.stringCellValue.toBigDecimal(scale, defaultIfNull, roundingMode)
                    }
                }

                CellType.NUMERIC -> {
                    this.numericCellValue.toBigDecimal(scale, defaultIfNull, roundingMode)
                }

                else -> {
                    this.stringCellValue.toBigDecimal(scale, defaultIfNull, roundingMode)
                }
            }
        } catch (e : Exception) {
            throw RuntimeException("Could not parse ${this.address} as BigDecimal", e)
        }
    }

fun Cell?.asInt(defaultIfNull: Int = 0): Int =
    if (this == null) {
        defaultIfNull
    } else {
        numericCellValue.toInt()
    }

fun Cell.value(value: String?, valueIfNull: String = "") {
    if (value == null) {
        setCellValue(valueIfNull)
    } else {
        setCellValue(value)
    }
}

fun Cell.value(value: Number?, valueIfNull: String = "") {
    if (value == null) {
        setCellValue(valueIfNull)
    } else {
        setCellValue(value.toDouble())
    }
}

fun Cell.value(value: LocalDate?, valueIfNull: String = "") {
    if (value == null) {
        setCellValue(valueIfNull)
    } else {
        setCellValue(Date.from(value.atStartOfDay(ZoneId.systemDefault()).toInstant()))
    }
}

fun Cell.style(style: CellStyle) {
    cellStyle = style
}

fun Cell.mergeColumnsToTheRight(columnsToMerge: Int) {
    sheet.addMergedRegion(CellRangeAddress(0, 0, columnIndex, columnIndex + columnsToMerge))
}
