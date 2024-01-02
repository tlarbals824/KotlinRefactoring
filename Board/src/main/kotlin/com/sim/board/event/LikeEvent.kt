package com.sim.board.event

data class LikeEvent (
    val postId: Long,
    val createdBy: String
){
}