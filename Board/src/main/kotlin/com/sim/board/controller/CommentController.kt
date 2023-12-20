package com.sim.board.controller

import com.sim.board.controller.dto.CommentCreateRequest
import com.sim.board.controller.dto.CommentUpdateRequest
import org.springframework.web.bind.annotation.*

@RestController
class CommentController() {

    @PostMapping("posts/{postId}/comments")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody request: CommentCreateRequest
    ) : Long {
        println(request)
        return 1L
    }

    @PutMapping("posts/{postId}/comments/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: CommentUpdateRequest
    ) : Long{
        println(request)
        return 1L
    }

    @DeleteMapping("posts/{postId}/comments/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestParam deletedBy: String
    ) {
        println(deletedBy)
    }
}