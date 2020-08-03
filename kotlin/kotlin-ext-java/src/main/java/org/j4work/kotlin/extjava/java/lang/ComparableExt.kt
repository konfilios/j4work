package org.j4work.kotlin.extjava.java.lang

fun <T: Comparable<T>> maxOfNullable(a: T?, b: T?): T? {
    return if (a != null && b!= null) {
        if (a >= b) {
            a
        } else {
            b
        }
    } else if (a != null) {
        a
    } else {
        b
    }
}

fun <T: Comparable<T>> minOfNullable(a: T?, b: T?): T? {
    return if (a != null && b != null) {
        if (a <= b) {
            a
        } else {
            b
        }
    } else if (a != null) {
        a
    } else {
        b
    }
}