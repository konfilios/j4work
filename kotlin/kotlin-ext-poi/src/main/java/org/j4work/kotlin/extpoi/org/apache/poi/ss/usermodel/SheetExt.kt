package org.j4work.kotlin.extpoi.org.apache.poi.ss.usermodel

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

/**
 * .
 */
fun Sheet.addRow(rownum: Int): Row =
    getRow(rownum) ?: createRow(rownum)

fun Sheet.autoSizeColumns(colNums: IntRange) {
    colNums.forEach {
        autoSizeColumn(it)
    }
}
