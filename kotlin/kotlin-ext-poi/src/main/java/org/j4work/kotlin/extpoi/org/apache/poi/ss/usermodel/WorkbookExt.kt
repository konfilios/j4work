package org.j4work.kotlin.extpoi.org.apache.poi.ss.usermodel

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileOutputStream

fun <R> createUseAndSave(destFile: File, block: (Workbook) -> R) =
    HSSFWorkbook().useAndSave(destFile, block)

fun <R> openUseAndSave(destFile: File, block: (Workbook) -> R): R {
    val tmpfile = destFile.parentFile
        .resolve("~${destFile.name}.${System.nanoTime()}.tmp")

    val funRet = WorkbookFactory.create(destFile).useAndSave(tmpfile, block)
    if (!tmpfile.renameTo(destFile)) {
        destFile.delete()
        throw Exception("Could not rename temporary file $tmpfile to $destFile")
    }
    return funRet
}

fun <R> Workbook.useAndSave(destFile: File, block: (Workbook) -> R) =
    this.use {
        val blockRet = block(this)
        FileOutputStream(destFile).use { fos ->
            it.write(fos)
        }
        blockRet
    }

fun Workbook.sheetWithName(name: String): Sheet =
    getSheet(name) ?: createSheet(name)
