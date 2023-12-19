package com.sim.kotlinjava.gettersetter

import java.time.LocalDate

class Student {
    @JvmField // 필드 접근 방식으로 사용할 수 있게 해줌
    var name: String? = null

    var birthDate: LocalDate? = null

    val age: Int = 10 // setter x

    var grade: String? = null
        private set // setter 막을 수 있음, 내부 함수를 통해 값 변경하도록 해야함

    fun changeGrade(grade: String) {
        this.grade = grade
    }
}
