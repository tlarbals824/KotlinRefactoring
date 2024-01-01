package com.sim.board.service

import com.sim.board.domain.Post
import com.sim.board.exception.PostNotDeletableException
import com.sim.board.exception.PostNotFoundException
import com.sim.board.exception.PostNotUpdatableException
import com.sim.board.repository.PostRepository
import com.sim.board.repository.TagRepository
import com.sim.board.service.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
    private val likeService: LikeService,
    private val tagRepository: TagRepository
) {
    @Transactional
    fun createPost(requestDto: PostCreateRequestDto): Long {
        println(requestDto.tags)
        val post = postRepository.save(requestDto.toEntity())
        return post.id
    }

    @Transactional
    fun updatePost(id: Long, postUpdateRequestDto: PostUpdateRequestDto): Long {
        val post: Post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
        if (post.createdBy != postUpdateRequestDto.updatedBy) throw PostNotUpdatableException()
        post.update(postUpdateRequestDto.title, postUpdateRequestDto.content, postUpdateRequestDto.updatedBy, postUpdateRequestDto.tags)
        return post.id
    }

    @Transactional
    fun deletePost(id: Long, deletedBy: String): Long {
        val post: Post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
        if (post.createdBy != deletedBy) throw PostNotDeletableException()
        postRepository.delete(post)
        return post.id
    }

    fun getPost(id: Long): PostDetailResponseDto {
        val postDetailResponseDto =
            postRepository.findByIdOrNull(id)?.toDetailResponseDto(
                likeService.countLike(id)
            ) ?: throw PostNotFoundException()
        return postDetailResponseDto
    }

    fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<PostSummaryResponseDto> {
        postSearchRequestDto.tag?.let {
            return tagRepository.findPageBy(pageRequest,it).toSummaryResponse(likeService::countLike)
        }
        return postRepository.findPageBy(pageRequest, postSearchRequestDto).toSummaryResponseDto(likeService::countLike)
    }
}
