package com.sim.board.controller.dto

import com.sim.board.service.dto.PostSummaryResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

data class PostSummaryResponse(
    val id: Long,
    val title: String,
    val createdBy: String,
    val createdAt: String,
    val tag : String?,
    val likeCount : Long = 0
)

fun Page<PostSummaryResponseDto>.toResponse(): PageImpl<PostSummaryResponse> {
    return PageImpl(this.content.map { it.toResponse() }, this.pageable, this.totalElements)
}

fun PostSummaryResponseDto.toResponse(): PostSummaryResponse {
    return PostSummaryResponse(
        id = this.id,
        title = this.title,
        createdBy = this.createdBy,
        createdAt = this.createdAt,
        tag = this.tag,
        likeCount = this.likeCount
    )
}
