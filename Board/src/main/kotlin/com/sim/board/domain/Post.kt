package com.sim.board.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Post(
    createBy : String,
    title: String,
    content: String
) : BaseEntity(createBy) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var title: String = title
        private set
    var content: String = content
        private set

    fun update(title: String, content: String, updatedBy: String) {
        this.title = title
        this.content = content
        super.update(updatedBy)
    }
}