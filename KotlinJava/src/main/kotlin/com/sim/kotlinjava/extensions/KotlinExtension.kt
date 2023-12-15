package com.sim.kotlinjava.extensions

fun String.first(): Char{
    return this[0]
}

fun String.addFirst(char: Char): String{
    return char + this.substring(0)
}

fun main() {
    println("ABCD".first())
    println("ABCD".addFirst('Z'))
}