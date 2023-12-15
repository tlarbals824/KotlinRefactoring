package com.fastcampus.kotlinspring.todo.api.model

import com.fastcampus.kotlinspring.todo.domain.Todo

data class TodoListResponse(
    val items: List<TodoResponse>,
) {
    fun size(): Int = items.size

    fun get(index: Int): TodoResponse = items[index]

    companion object {
        fun of(todoList: List<Todo>): TodoListResponse = TodoListResponse(todoList.map { TodoResponse.of(it) })
    }
}