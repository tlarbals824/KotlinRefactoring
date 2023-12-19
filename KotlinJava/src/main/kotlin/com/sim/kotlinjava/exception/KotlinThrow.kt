package com.sim.kotlinjava.exception

import java.io.IOException
import kotlin.jvm.Throws

class KotlinThrow {

    @Throws(IOException::class)
    fun throwIOException() {
        throw IOException("체크드 익셉션 IOException 발생")
    }
}

fun main() {
    val javaThrow = JavaThrow()
    javaThrow.throwIOException()

    val kotlinThrow = KotlinThrow()
    kotlinThrow.throwIOException()
}
