package com.sim.board.controller

import com.sim.board.controller.dto.*
import com.sim.board.service.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun createPost(@RequestBody request: PostCreateRequest): Long = postService.createPost(request.toDto())

    @PutMapping("/{id}")
    fun updatePost(
        @PathVariable id: Long,
        @RequestBody request: PostUpdateRequest
    ): Long = postService.updatePost(id, request.toDto())

    @DeleteMapping("/{id}")
    fun deletePost(
        @PathVariable id: Long,
        @RequestParam createdBy: String
    ) = postService.deletePost(id, createdBy)

    @GetMapping("/{id}")
    fun getPost(
        @PathVariable id: Long
    ): PostDetailResponse {
        return PostDetailResponse(
            id = 1L,
            title = "title",
            content = "content",
            createdBy = "createdBy",
            createdAt = LocalDateTime.now()
        )
    }

    @GetMapping
    fun getPosts(
        pageable: Pageable,
        postSearchRequest: PostSearchRequest
    ): Page<PostSummaryResponse> {
        return Page.empty()
    }
}
