package com.sim.board.service.dto

import com.sim.board.domain.Post

data class PostCreateRequestDto(
    val title: String,
    val content: String,
    val createdBy: String
) {

    fun toEntity() = Post(
        title = this.title,
        content = this.content,
        createBy = this.createdBy
    )
}
