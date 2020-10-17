package org.j4work.kotlin.extpoi.org.apache.poi.ss.usermodel

import org.apache.poi.ss.usermodel.Sheet

class RowAppender(
    val sheet: Sheet,
    startRowNum: Int = 0
) {
    private var curRowNum = startRowNum
        get

    fun addRow(
        init: CellAppender.() -> Unit = {}
    ): RowAppender {
        val row = sheet.addRow(curRowNum)
        curRowNum++
        CellAppender(row).init()
        return this
    }

    fun skip(skipCount: Int = 1): RowAppender {
        curRowNum += skipCount
        return this
    }
}