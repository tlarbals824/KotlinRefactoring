package com.sim.board.service

import com.sim.board.domain.Post
import com.sim.board.exception.PostNotDeletableException
import com.sim.board.exception.PostNotFoundException
import com.sim.board.exception.PostNotUpdatableException
import com.sim.board.repository.PostRepository
import com.sim.board.service.dto.PostCreateRequestDto
import com.sim.board.service.dto.PostUpdateRequestDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository
) {
    @Transactional
    fun createPost(requestDto: PostCreateRequestDto): Long {
        val post = postRepository.save(requestDto.toEntity())
        return post.id
    }

    @Transactional
    fun updatePost(id: Long, postUpdateRequestDto: PostUpdateRequestDto) : Long {
        val post: Post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
        if(post.createdBy != postUpdateRequestDto.updatedBy) throw PostNotUpdatableException()
        post.update(postUpdateRequestDto.title, postUpdateRequestDto.content, postUpdateRequestDto.updatedBy)
        return post.id
    }

    @Transactional
    fun deletePost(id: Long, deletedBy: String): Long {
        val post: Post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
        if(post.createdBy != deletedBy) throw PostNotDeletableException()
        postRepository.delete(post)
        return post.id
    }
}