package com.sim.board.service.dto

import com.sim.board.controller.dto.CommentResponse
import com.sim.board.domain.Comment
import java.time.LocalDateTime

data class CommentResponseDto(
    val id: Long,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)

fun CommentResponseDto.toResponse(): CommentResponse {
    return CommentResponse(
        id = this.id,
        content = this.content,
        createdBy = this.createdBy,
        createdAt = this.createdAt,
    )
}

fun Comment.toCommentResponseDto(): CommentResponseDto {
    return CommentResponseDto(
        id = this.id,
        content = this.content,
        createdBy = this.createdBy,
        createdAt = this.createdAt,
    )
}

fun List<CommentResponseDto>.toResponse(): List<CommentResponse> {
    return this.map { it.toResponse() }
}
