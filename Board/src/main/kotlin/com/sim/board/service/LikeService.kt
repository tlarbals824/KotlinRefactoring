package com.sim.board.service

import com.sim.board.domain.Like
import com.sim.board.exception.PostNotFoundException
import com.sim.board.repository.LikeRepository
import com.sim.board.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository
) {
    fun createLike(postId: Long, createdBy: String): Long {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()
        return likeRepository.save(Like(post, createdBy)).id
    }

    fun countLike(postId: Long): Long {
        return likeRepository.countByPostId(postId)
    }
}