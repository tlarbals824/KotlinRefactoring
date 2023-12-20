package com.sim.board.service.dto

import com.sim.board.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

data class PostSummaryResponseDto(
    val id: Long,
    val title: String,
    val createdBy: String,
    val createdAt: String
)

fun Post.toSummaryResponseDto(): PostSummaryResponseDto {
    return PostSummaryResponseDto(
        id = this.id,
        title = this.title,
        createdBy = this.createdBy,
        createdAt = this.createdAt.toString()
    )
}

fun Page<Post>.toSummaryResponseDto(): PageImpl<PostSummaryResponseDto> {
    return PageImpl(this.content.map { it.toSummaryResponseDto() }, this.pageable, this.totalElements)
}