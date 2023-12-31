package com.sim.board.service

import com.sim.board.domain.Comment
import com.sim.board.domain.Post
import com.sim.board.exception.CommentNotDeletableException
import com.sim.board.exception.CommentNotFoundException
import com.sim.board.exception.CommentNotUpdatableException
import com.sim.board.exception.PostNotFoundException
import com.sim.board.repository.CommentRepository
import com.sim.board.repository.PostRepository
import com.sim.board.service.dto.CommentCreateRequestDto
import com.sim.board.service.dto.CommentUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class CommentServiceTest(
    private val commentService: CommentService,
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
) : BehaviorSpec({
    given("댓글 생성시") {
        `when`("요청이 정상적으로 들어오면") {
            val postId = postRepository.save(
                Post(
                    title = "제목",
                    content = "내용",
                    createBy = "작성자",
                    tags = listOf("태그1", "태그2")
                )
            ).id
            val commentId = commentService.createComment(
                postId,
                CommentCreateRequestDto(
                    content = "댓글 내용",
                    createdBy = "작성자"
                )
            )
            then("정상 생성됨을 확인한다.") {
                commentId shouldBeGreaterThan 0L
                val comment = commentRepository.findByIdOrNull(commentId)
                comment shouldNotBe null
                comment?.content shouldBe "댓글 내용"
                comment?.createdBy shouldBe "작성자"
            }
        }
        `when`("게시글이 존재하지 않으면") {
            val postId = 0L
            then("게시글이 존재하지 않음 예외가 발생한다.") {
                shouldThrow<PostNotFoundException> {
                    commentService.createComment(
                        postId,
                        CommentCreateRequestDto(
                            content = "댓글 내용",
                            createdBy = "작성자"
                        )
                    )
                }
            }
        }
    }
    given("댓글 수정시") {
        `when`("요청이 정상적으로 들어오면") {
            val post = postRepository.save(
                Post(
                    title = "제목",
                    content = "내용",
                    createBy = "작성자",
                    tags = listOf("태그1", "태그2")
                )
            )
            val comment = commentRepository.save(
                Comment(
                    content = "댓글 내용",
                    createdBy = "작성자",
                    post = post
                )
            )
            val updatedCommentId = commentService.updateComment(
                post.id,
                comment.id,
                CommentUpdateRequestDto(
                    content = "수정된 댓글 내용",
                    updatedBy = "작성자"
                )
            )
            then("정상 수정됨을 확인한다.") {
                updatedCommentId shouldBe comment.id
                val findComment = commentRepository.findByIdOrNull(comment.id) ?: throw CommentNotFoundException()
                findComment shouldNotBe null
                findComment.content shouldBe "수정된 댓글 내용"
                findComment.updatedBy shouldBe "작성자"
            }
        }
        `when`("생성자와 수정자가 다르면") {
            val post = postRepository.save(
                Post(
                    title = "제목",
                    content = "내용",
                    createBy = "작성자",
                    tags = listOf("태그1", "태그2")
                )
            )
            val commentId = commentRepository.save(
                Comment(
                    content = "댓글 내용",
                    createdBy = "작성자",
                    post = post
                )
            ).id
            then("수정할 수 없는 댓글 예외가 발생한다.") {
                shouldThrow<CommentNotUpdatableException> {
                    commentService.updateComment(
                        post.id,
                        commentId,
                        CommentUpdateRequestDto(
                            content = "수정된 댓글 내용",
                            updatedBy = "수정자"
                        )
                    )
                }
            }
        }
    }
    given("댓글을 삭제시") {
        val post = postRepository.save(
            Post(
                title = "제목",
                content = "내용",
                createBy = "작성자",
                tags = listOf("태그1", "태그2")
            )
        )
        val comment1 = commentRepository.save(
            Comment(
                content = "댓글 내용",
                createdBy = "작성자1",
                post = post
            )
        )
        val comment2 = commentRepository.save(
            Comment(
                content = "댓글 내용",
                createdBy = "작성자2",
                post = post
            )
        )
        `when`("요청이 정상적으로 들어오면") {
            commentService.deleteComment(
                post.id,
                comment1.id,
                "작성자1"
            )
            then("정상 삭제됨을 확인한다.") {
                val findComment = commentRepository.findByIdOrNull(comment1.id)
                findComment shouldBe null
            }
        }
        `when`("생성자와 삭제자가 다르면") {
            then("삭제할 수 없는 댓글 예외가 발생한다.") {
                shouldThrow<CommentNotDeletableException> {
                    commentService.deleteComment(
                        post.id,
                        comment2.id,
                        "삭제자"
                    )
                }
            }
        }
    }
})
