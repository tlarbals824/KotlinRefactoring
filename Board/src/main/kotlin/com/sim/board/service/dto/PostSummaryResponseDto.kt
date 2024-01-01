package com.sim.board.service.dto

import com.sim.board.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

data class PostSummaryResponseDto(
    val id: Long,
    val title: String,
    val createdBy: String,
    val createdAt: String,
    val tag : String?=null,
    val likeCount : Long
)

fun Post.toSummaryResponseDto(countLike: (Long) -> Long): PostSummaryResponseDto {
    return PostSummaryResponseDto(
        id = this.id,
        title = this.title,
        createdBy = this.createdBy,
        createdAt = this.createdAt.toString(),
        tag = tags.firstOrNull()?.name,
        likeCount = countLike(this.id)
    )
}

fun Page<Post>.toSummaryResponseDto(countLike: (Long)->Long): PageImpl<PostSummaryResponseDto> {
    return PageImpl(this.content.map { it.toSummaryResponseDto(countLike) }, this.pageable, this.totalElements)
}
