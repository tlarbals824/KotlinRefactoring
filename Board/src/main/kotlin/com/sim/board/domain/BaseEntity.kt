package com.sim.board.domain

import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity(
    createBy: String
) {
    val createdBy: String = createBy
    val createdAt: LocalDateTime = LocalDateTime.now()
    var updatedBy: String? = null
        protected set
    var updatedAt: LocalDateTime? = null
        protected set

    fun update(updatedBy: String) {
        this.updatedBy = updatedBy
        this.updatedAt = LocalDateTime.now()
    }
}
