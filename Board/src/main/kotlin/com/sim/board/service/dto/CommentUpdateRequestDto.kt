package com.sim.board.service.dto

import com.sim.board.controller.dto.CommentUpdateRequest

data class CommentUpdateRequestDto(
    val content: String,
    val updatedBy: String,
)

fun CommentUpdateRequest.toDto(): CommentUpdateRequestDto {
    return CommentUpdateRequestDto(
        content = this.content,
        updatedBy = this.updatedBy,
    )
}
