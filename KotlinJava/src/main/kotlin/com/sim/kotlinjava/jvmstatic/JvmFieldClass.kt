package com.sim.kotlinjava.jvmstatic

class JvmFieldClass {
    companion object{
        @JvmField
        var id = 1234

        const val CODE = 1234
    }
}

object JvmFieldObject{
    @JvmField
    val name = "tony"

    const val NAME = "tony"
}

fun main() {
    val id = JvmFieldObject.name

    val name = JvmFieldObject.name
}