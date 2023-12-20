package com.sim.board.service

import com.sim.board.exception.CommentNotDeletableException
import com.sim.board.exception.CommentNotFoundException
import com.sim.board.exception.CommentNotUpdatableException
import com.sim.board.exception.PostNotFoundException
import com.sim.board.repository.CommentRepository
import com.sim.board.repository.PostRepository
import com.sim.board.service.dto.CommentCreateRequestDto
import com.sim.board.service.dto.CommentUpdateRequestDto
import com.sim.board.service.dto.toEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
) {

    @Transactional
    fun createComment(postId: Long, request: CommentCreateRequestDto): Long {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()
        return commentRepository.save(request.toEntity(post)).id
    }

    @Transactional
    fun updateComment(postId: Long, commentId: Long, request: CommentUpdateRequestDto): Long {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw CommentNotFoundException()
        if (comment.createdBy != request.updatedBy) throw CommentNotUpdatableException()
        comment.update(request.content, request.updatedBy)
        return comment.id
    }

    @Transactional
    fun deleteComment(postId: Long, commentId: Long, deletedBy: String) {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw CommentNotFoundException()
        if (comment.createdBy != deletedBy) throw CommentNotDeletableException()
        commentRepository.delete(comment)
    }
}
