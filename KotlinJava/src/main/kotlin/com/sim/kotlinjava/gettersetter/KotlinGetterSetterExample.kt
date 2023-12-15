package com.sim.kotlinjava.gettersetter

fun main() {

    // 자바 스타일
    val person = Person()
    person.setName("심규민")
    person.setAge(24)
    person.setAddress("영등포")

    println(person.getName())
    println(person.getAge())

    // 코틀린 스타일
    val person2 = Person()
    person2.name = "심규민"
    person2.age = 24
//    person2.address = "영등포"

    println(person2.name)
    println(person2.age)
//    println(person2.address)

    // 프로퍼티가 없음에도 게터 메소드
    println(person2.uuid)

    // getter setter 네이밍 맞추지 않는다면 컴파일러가 프로퍼티로 만들지 않는다.
//    person2.address = ""
    println(person2.myAddress())
}