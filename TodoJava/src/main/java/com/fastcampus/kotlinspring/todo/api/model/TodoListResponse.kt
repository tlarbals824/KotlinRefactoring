package com.fastcampus.kotlinspring.todo.api.model

import com.fastcampus.kotlinspring.todo.domain.Todo

data class TodoListResponse(
    val items: List<TodoResponse>,
) {
    fun size():Int = items.size

    fun get(index: Int):TodoResponse = items.get(index)

    companion object{
        @JvmStatic
        fun of(todoList: List<Todo>):TodoListResponse{
            val items = todoList.stream()
                .map(TodoResponse::of)
                .toList()
            return TodoListResponse(items)
        }
    }
}