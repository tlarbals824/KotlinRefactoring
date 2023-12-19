package com.sim.board.service

import com.sim.board.domain.Post
import com.sim.board.exception.PostNotDeletableException
import com.sim.board.exception.PostNotFoundException
import com.sim.board.exception.PostNotUpdatableException
import com.sim.board.repository.PostRepository
import com.sim.board.service.dto.PostCreateRequestDto
import com.sim.board.service.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class PostServiceTest(
    private val postSerive: PostService,
    private val postRepository: PostRepository
) : BehaviorSpec({
    given("게시글 생성시") {
        `when`("게시글 요청이 정상적으로 들어오면") {
            val postId: Long = postSerive.createPost(
                PostCreateRequestDto(
                    title = "제목",
                    content = "내용",
                    createdBy = "작성자"
                )
            )
            then("게시글이 정상적으로 생성됨을 확인한다.") {
                postId shouldBeGreaterThan 0L
                val post = postRepository.findByIdOrNull(postId)
                post?.title shouldBe "제목"
                post?.content shouldBe "내용"
                post?.createdBy shouldBe "작성자"
            }
        }
    }
    given("게시글 수정시") {
        val post = postRepository.save(
            Post(
                title = "제목",
                content = "내용",
                createBy = "작성자"
            )
        )
        `when`("정상 수정시") {
            val updatedId: Long = postSerive.updatePost(
                post.id,
                PostUpdateRequestDto(
                    title = "수정된 제목",
                    content = "수정된 내용",
                    updatedBy = "작성자"
                )
            )
            then("게시글이 정상적으로 수정됨을 확인한다.") {
                post.id shouldBe updatedId
                val updated = postRepository.findByIdOrNull(post.id)
                updated shouldNotBe null
                updated?.title shouldBe "수정된 제목"
                updated?.content shouldBe "수정된 내용"
                updated?.updatedBy shouldBe "작성자"
            }
        }
        `when`("게시글을 찾을 수 없을 때") {
            then("예외가 발생한다.") {
                val exception = shouldThrow<PostNotFoundException> {
                    postSerive.updatePost(
                        0L,
                        PostUpdateRequestDto(
                            title = "수정된 제목",
                            content = "수정된 내용",
                            updatedBy = "작성자"
                        )
                    )
                }
                exception.message shouldBe "해당 게시글을 찾을 수 없습니다."
            }
        }
        `when`("작성자가 동일하지 않으면") {
            then("수정할 수 없는 게시물 입니다 예외가 발생한다.") {
                shouldThrow<PostNotUpdatableException> {
                    postSerive.updatePost(
                        post.id,
                        PostUpdateRequestDto(
                            title = "수정된 제목",
                            content = "수정된 내용",
                            updatedBy = "수정자"
                        )
                    )
                }
            }
        }
    }
    given("게시글 삭제시") {
        val post = postRepository.save(
            Post(
                title = "제목",
                content = "내용",
                createBy = "작성자"
            )
        )
        `when`("정상 삭제시") {
            val postId: Long = postSerive.deletePost(post.id, "작성자")
            then("게시글이 정상적으로 삭제됨을 확인한다.") {
                post.id shouldBe postId
                val deleted = postRepository.findByIdOrNull(post.id)
                deleted shouldBe null
            }
        }
        `when`("작성자가 동일하지 않으면") {
            then("삭제할 수 없는 게시물 입니다 예외가 발생한다.") {
                shouldThrow<PostNotDeletableException> {
                    postSerive.deletePost(post.id, "수정자")
                }
            }
        }
    }
})
