package com.sim.board.service.dto

import com.sim.board.domain.Post

data class PostCreateRequestDto(
    val title: String,
    val content: String,
    val createdBy: String,
    val tags : List<String> = emptyList()
) {

    fun toEntity() = Post(
        title = this.title,
        content = this.content,
        createBy = this.createdBy,
        tags = this.tags
    )
}
