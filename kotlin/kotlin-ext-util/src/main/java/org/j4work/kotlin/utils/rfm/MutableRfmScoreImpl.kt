package org.j4work.kotlin.utils.rfm

internal class MutableRfmScoreImpl<T>(
    override val item: T,
    override val recencyValue: Int,
    override val frequencyValue: Int,
        override val monetaryValue: Int
) : RfmScore<T> {

    override var recencyCategory: Int = 0
        internal set

    override var frequencyCategory: Int = 0
        internal set

    override var monetaryCategory: Int = 0
        internal set
}