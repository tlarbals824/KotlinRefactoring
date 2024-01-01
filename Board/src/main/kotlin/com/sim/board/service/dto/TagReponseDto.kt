package com.sim.board.service.dto

import com.sim.board.domain.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl


fun Page<Tag>.toSummaryResponse(countLike: (Long) -> Long): PageImpl<PostSummaryResponseDto> {
    return PageImpl(
        this.content.map { it.toSummaryResponse(countLike) },
        this.pageable,
        this.totalElements
    )
}


fun Tag.toSummaryResponse(countLike: (Long) -> Long): PostSummaryResponseDto {
    return PostSummaryResponseDto(
        id = post.id,
        title = post.title,
        createdBy = post.createdBy,
        createdAt = post.createdAt,
        tag = name,
        likeCount = countLike(post.id)
    )
}
