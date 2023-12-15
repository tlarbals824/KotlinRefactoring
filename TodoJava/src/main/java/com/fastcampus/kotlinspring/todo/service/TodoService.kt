package com.fastcampus.kotlinspring.todo.service

import com.fastcampus.kotlinspring.todo.api.model.TodoRequest
import com.fastcampus.kotlinspring.todo.domain.Todo
import com.fastcampus.kotlinspring.todo.domain.TodoRepository
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class TodoService(
    private val todoRepository: TodoRepository
) {
    @Transactional(readOnly = true)
    fun findAll() = todoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))

    @Transactional(readOnly = true)
    fun findById(id: Long) = todoRepository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    @Transactional
    fun create(request: TodoRequest): Todo {
        checkNotNull(request) { "TodoRequest is null" }

        val todo = Todo.builder()
            .title(request.title)
            .description(request.description)
            .done(false)
            .createdAt(LocalDateTime.now())
            .build()

        return todoRepository.save(todo)
    }

    @Transactional
    fun update(id: Long, request: TodoRequest) : Todo{
        checkNotNull(request) { "TodoRequest is null" }

        val todo = findById(id)
        todo.update(request.title, request.description, request.done)
        return todo
    }

    fun delete(id: Long){
        todoRepository.deleteById(id)
    }
}