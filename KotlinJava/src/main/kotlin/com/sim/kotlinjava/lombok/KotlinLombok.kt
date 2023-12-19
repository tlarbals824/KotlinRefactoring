package com.sim.kotlinjava.lombok

fun main() {
    val hero = Hero()

    hero.name = "심규민"
    println(hero.name)

    hero.address = "영등포"
    println(hero.address)

    val hero2 = HeroKt("심규민", 24, "영등포")
    print(hero2)
}
