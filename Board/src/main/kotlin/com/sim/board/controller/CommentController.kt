package com.sim.board.controller

import com.sim.board.controller.dto.CommentCreateRequest
import com.sim.board.controller.dto.CommentUpdateRequest
import com.sim.board.service.CommentService
import com.sim.board.service.dto.toDto
import org.springframework.web.bind.annotation.*

@RestController
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping("posts/{postId}/comments")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody request: CommentCreateRequest
    ) : Long = commentService.createComment(postId, request.toDto())

    @PutMapping("posts/{postId}/comments/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: CommentUpdateRequest
    ) : Long = commentService.updateComment(postId, commentId, request.toDto())

    @DeleteMapping("posts/{postId}/comments/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestParam deletedBy: String
    ) = commentService.deleteComment(postId, commentId, deletedBy)
}