package com.sim.board.controller

import com.sim.board.service.LikeService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LikeController (
    private val likeService: LikeService
){

    @PostMapping("posts/{postId}/likes")
    fun createLike(
        @PathVariable postId: Long,
        @RequestParam createdBy: String
    ):Long = likeService.createLike(postId, createdBy)

}