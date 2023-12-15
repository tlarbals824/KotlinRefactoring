package com.fastcampus.kotlinspring.todo.api.model

import com.fastcampus.kotlinspring.todo.domain.Todo
import org.springframework.util.Assert
import java.time.LocalDateTime

data class TodoResponse(
    val id: Long,
    val title: String,
    val description: String,
    val done : Boolean,
    val createdAt : LocalDateTime,
    val updatedAt : LocalDateTime?,
) {
    companion object{
        @JvmStatic
        fun of(todo: Todo) : TodoResponse{
            Assert.notNull(todo, "Todo is null")

            return TodoResponse(
                id =  todo.id,
                title = todo.title,
                description = todo.description,
                done = todo.done,
                createdAt = todo.createdAt,
                updatedAt = todo.updatedAt
            )
        }
    }
}