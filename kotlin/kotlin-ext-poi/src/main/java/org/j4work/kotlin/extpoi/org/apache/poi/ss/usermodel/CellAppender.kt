package org.j4work.kotlin.extpoi.org.apache.poi.ss.usermodel

import org.apache.poi.hssf.util.CellReference
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType

class CellAppender(
    val row: org.apache.poi.ss.usermodel.Row,
    startColNum: Int = 0
) {
    private var curColNum = startColNum
        get

    fun cell(
        col: String,
        type: CellType = CellType.BLANK,
        init: Cell.() -> Unit = {}
    ) : CellAppender {
        curColNum = CellReference.convertColStringToIndex(col)
        return addCell(type, init)
    }

    fun addCell(
        type: CellType = CellType.BLANK,
        init: Cell.() -> Unit = {}
    ): CellAppender {
        row.cell(curColNum, type).init()
        curColNum++
        return this
    }

    fun skipCells(skipCount: Int = 1): CellAppender {
        curColNum += skipCount
        return this
    }

}