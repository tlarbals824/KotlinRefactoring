package com.sim.board.controller.dto

import com.sim.board.service.dto.PostDetailResponseDto
import java.time.LocalDateTime

data class PostDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val comments: List<CommentResponse> = emptyList(),
)

fun PostDetailResponseDto.toResponse(): PostDetailResponse {
    return PostDetailResponse(
        id = this.id,
        title = this.title,
        content = this.content,
        createdBy = this.createdBy,
        createdAt = this.createdAt
    )
}
