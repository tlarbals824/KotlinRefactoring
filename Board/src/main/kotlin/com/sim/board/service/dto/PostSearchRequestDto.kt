package com.sim.board.service.dto

import com.sim.board.controller.dto.PostSearchRequest

data class PostSearchRequestDto(
    val title: String? = null,
    val createdBy: String? = null
)

fun PostSearchRequest.toDto(): PostSearchRequestDto {
    return PostSearchRequestDto(
        title = this.title,
        createdBy = this.createdBy
    )
}
