package com.sim.board.service.dto

import com.sim.board.controller.dto.CommentCreateRequest
import com.sim.board.domain.Comment
import com.sim.board.domain.Post

data class CommentCreateRequestDto(
    val content: String,
    val createdBy: String
)

fun CommentCreateRequest.toDto(): CommentCreateRequestDto {
    return CommentCreateRequestDto(
        content = this.content,
        createdBy = this.createdBy
    )
}

fun CommentCreateRequestDto.toEntity(post: Post): Comment {
    return Comment(
        content = this.content,
        post = post,
        createdBy = this.createdBy
    )
}
