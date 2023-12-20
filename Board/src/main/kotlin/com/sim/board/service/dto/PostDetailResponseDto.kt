package com.sim.board.service.dto

import com.sim.board.domain.Post
import java.time.LocalDateTime

data class PostDetailResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val comments: List<CommentResponseDto> = emptyList(),
)

fun Post.toDetailResponseDto(): PostDetailResponseDto {
    return PostDetailResponseDto(
        id = this.id,
        title = this.title,
        content = this.content,
        createdBy = this.createdBy,
        createdAt = this.createdAt,
        comments = this.comments.map { it.toCommentResponseDto() }
    )
}
