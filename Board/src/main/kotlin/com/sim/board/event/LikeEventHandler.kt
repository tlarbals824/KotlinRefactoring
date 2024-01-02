package com.sim.board.event

import com.sim.board.domain.Like
import com.sim.board.exception.PostNotFoundException
import com.sim.board.repository.LikeRepository
import com.sim.board.repository.PostRepository
import com.sim.board.utils.RedisUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Component
class LikeEventHandler(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
    private val redisUtils: RedisUtils
) {

    @TransactionalEventListener
    @Async
    @Transactional
    fun handleLikeEvent(likeEvent: LikeEvent) {
        val post = postRepository.findByIdOrNull(likeEvent.postId) ?: throw PostNotFoundException()
        redisUtils.increment("like:$likeEvent.postId")
        likeRepository.save(Like(post,likeEvent.createdBy)).id
    }
}