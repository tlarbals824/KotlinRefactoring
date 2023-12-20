package com.sim.board.controller

import com.sim.board.controller.dto.*
import com.sim.board.service.PostService
import com.sim.board.service.dto.toDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

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
    ): PostDetailResponse = postService.getPost(id).toResponse()

    @GetMapping
    fun getPosts(
        pageable: Pageable,
        postSearchRequest: PostSearchRequest
    ): Page<PostSummaryResponse> = postService.findPageBy(pageable, postSearchRequest.toDto()).toResponse()
}
