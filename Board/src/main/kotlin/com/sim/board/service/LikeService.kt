package com.sim.board.service

import com.sim.board.domain.Like
import com.sim.board.exception.PostNotFoundException
import com.sim.board.repository.LikeRepository
import com.sim.board.repository.PostRepository
import com.sim.board.utils.RedisUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
    private val redisUtils: RedisUtils
) {
    fun createLike(postId: Long, createdBy: String): Long {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()
        redisUtils.increment("like:$postId")
        return likeRepository.save(Like(post, createdBy)).id
    }

    fun countLike(postId: Long): Long {
        val redisLikeCount = redisUtils.getData("like:$postId")
        redisLikeCount?.toString()?.toLong()?.let {
            return it
        }
        with(likeRepository.countByPostId(postId)) {
            redisUtils.setData("like:$postId", this)
            return this
        }
    }
}