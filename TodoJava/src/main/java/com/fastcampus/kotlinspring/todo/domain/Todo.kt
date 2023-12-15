package com.fastcampus.kotlinspring.todo.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table
import lombok.Builder
import java.time.LocalDateTime

@Entity
@Table(name = "todos")
data class Todo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,

    @Column(name="title")
    var title: String,

    @Lob
    @Column(name = "description")
    var description : String,

    @Column(name="done")
    var done : Boolean,

    @Column(name = "created_at")
    var createdAt : LocalDateTime,

    @Column(name = "updated_at")
    var updatedAt : LocalDateTime? = null,
) {
    fun update(title: String, description: String, done: Boolean){
        this.title = title
        this.description = description
        this.done = done
        this.updatedAt = LocalDateTime.now()
    }
}