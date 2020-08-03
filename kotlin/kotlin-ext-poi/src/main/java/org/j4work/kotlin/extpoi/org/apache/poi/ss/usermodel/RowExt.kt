package org.j4work.kotlin.extpoi.org.apache.poi.ss.usermodel

import org.apache.poi.hssf.util.CellReference
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.util.CellReference.convertColStringToIndex

operator fun Row.get(i: Int): Cell? = getCell(i)

operator fun Row.get(i: String): Cell? = getCell(convertColStringToIndex(i))


fun Row.cell(
    col: String,
    type: CellType = CellType.BLANK,
    init: Cell.() -> Unit = {}
) =
    cell(CellReference.convertColStringToIndex(col), type, init)

fun Row.cell(
    colNum: Int,
    type: CellType = CellType.BLANK,
    init: Cell.() -> Unit = {}
): Row {
    cell(colNum, type).init()
    return this
}

fun Row.cell(
    colNum: Int,
    type: CellType = CellType.BLANK
): Cell =
    getCell(colNum) ?: createCell(colNum, type)
