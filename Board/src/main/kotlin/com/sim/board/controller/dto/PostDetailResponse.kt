package com.sim.board.controller.dto

import com.sim.board.service.dto.PostDetailResponseDto
import com.sim.board.service.dto.toResponse
import java.time.LocalDateTime

data class PostDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val comments: List<CommentResponse> = emptyList(),
    val tags : List<String> = emptyList()
)

fun PostDetailResponseDto.toResponse(): PostDetailResponse {
    return PostDetailResponse(
        id = this.id,
        title = this.title,
        content = this.content,
        createdBy = this.createdBy,
        createdAt = this.createdAt,
        comments = this.comments.toResponse(),
        tags = this.tags
    )
}
