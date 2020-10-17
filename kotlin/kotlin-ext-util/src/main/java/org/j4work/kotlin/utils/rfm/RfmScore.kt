package org.j4work.kotlin.utils.rfm

interface RfmScore<T> {
    val item: T

    val recencyValue: Int

    val frequencyValue: Int

    val monetaryValue: Int

    val recencyCategory: Int

    val frequencyCategory: Int

    val monetaryCategory: Int

    fun totalScore() =
        "${recencyCategory}-${frequencyCategory}-${monetaryCategory}"
}