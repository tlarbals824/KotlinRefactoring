package com.sim.board.service

import com.sim.board.domain.Comment
import com.sim.board.domain.Post
import com.sim.board.exception.PostNotDeletableException
import com.sim.board.exception.PostNotFoundException
import com.sim.board.exception.PostNotUpdatableException
import com.sim.board.repository.CommentRepository
import com.sim.board.repository.PostRepository
import com.sim.board.repository.TagRepository
import com.sim.board.service.dto.PostCreateRequestDto
import com.sim.board.service.dto.PostSearchRequestDto
import com.sim.board.service.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.testcontainers.perSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.testcontainers.containers.GenericContainer

@SpringBootTest
class PostServiceTest(
    private val postSerive: PostService,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val tagRepository: TagRepository,
    private val likeService: LikeService
) : BehaviorSpec({
    val redisContainer = GenericContainer<Nothing>("redis:5.0.3-alpine")
    afterSpec({
        redisContainer.stop()
    })
    beforeSpec {
        redisContainer.portBindings.add("16379:6379")
        redisContainer.start()
        listener(redisContainer.perSpec())
        postRepository.saveAll(
            listOf(
                Post(
                    title = "제목1",
                    content = "내용1",
                    createBy = "작성자",
                    tags = listOf("태그1", "태그2")
                ),
                Post(
                    title = "제목2",
                    content = "내용2",
                    createBy = "작성자",
                    tags = listOf("태그1", "태그2")
                ),
                Post(
                    title = "제목3",
                    content = "내용3",
                    createBy = "작성자",
                    tags = listOf("태그1", "태그2")
                ),
                Post(
                    title = "제목4",
                    content = "내용4",
                    createBy = "작성자",
                    tags = listOf("태그1", "태그2")
                ),
                Post(
                    title = "제목5",
                    content = "내용5",
                    createBy = "작성자",
                    tags = listOf("태그1", "태그2")
                ),
                Post(
                    title = "제목6",
                    content = "내용6",
                    createBy = "작성자",
                    tags = listOf("태그1", "태그2")
                ),
                Post(
                    title = "제목7",
                    content = "내용7",
                    createBy = "작성자",
                    tags = listOf("태그1", "태그2")
                ),
                Post(
                    title = "제목8",
                    content = "내용8",
                    createBy = "작성자",
                    tags = listOf("태그1", "태그3")
                ),
                Post(
                    title = "제목9",
                    content = "내용9",
                    createBy = "작성자",
                    tags = listOf("태그1", "태그3")
                ),
                Post(
                    title = "제목10",
                    content = "내용10",
                    createBy = "작성자",
                    tags = listOf("태그1", "태그3")
                )
            )
        )
    }
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
        `when`("태그가 추가되면"){
            val postId: Long = postSerive.createPost(
                PostCreateRequestDto(
                    title = "제목",
                    content = "내용",
                    createdBy = "작성자",
                    tags = listOf("태그1", "태그2")
                )
            )
            then("태그가 정상적으로 추가됨을 확인한다."){
                val tags = tagRepository.findByPostId(postId)
                tags.size shouldBe 2
                tags[0].name shouldBe "태그1"
                tags[1].name shouldBe "태그2"
            }
        }
    }
    given("게시글 수정시") {
        val post = postRepository.save(
            Post(
                title = "제목",
                content = "내용",
                createBy = "작성자",
                tags = listOf("태그1", "태그2")
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
        `when`("태그가 수정됐을 때"){
            val updatedId: Long = postSerive.updatePost(
                post.id,
                PostUpdateRequestDto(
                    title = "수정된 제목",
                    content = "수정된 내용",
                    updatedBy = "작성자",
                    tags = listOf("태그1", "태그2", "태그3")
                )
            )
            then("태그가 정상적으로 수정됨을 확인한다."){
                val tags = tagRepository.findByPostId(updatedId)
                tags.size shouldBe 3
                tags[0].name shouldBe "태그1"
                tags[1].name shouldBe "태그2"
                tags[2].name shouldBe "태그3"
            }
            then("태그가 순서만 변경 됐을 때 정상적으로 변경됨을 확인한다."){
                val updatedId: Long = postSerive.updatePost(
                    post.id,
                    PostUpdateRequestDto(
                        title = "수정된 제목",
                        content = "수정된 내용",
                        updatedBy = "작성자",
                        tags = listOf("태그3", "태그2", "태그1")
                    )
                )
                val tags = tagRepository.findByPostId(updatedId)
                tags.size shouldBe 3
            }
        }
    }
    given("게시글 삭제시") {
        val post = postRepository.save(
            Post(
                title = "제목",
                content = "내용",
                createBy = "작성자",
                tags = listOf("태그1", "태그2")
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
    given("게시글 상제조회시") {
        val saved: Post = postRepository.save(
            Post(
                title = "제목",
                content = "내용",
                createBy = "작성자",
                tags = listOf("태그1", "태그2")
            )
        )
        likeService.createLike(saved.id, "sim")
        likeService.createLike(saved.id, "sim")
        likeService.createLike(saved.id, "sim")
        `when`("정상 조회시") {
            val post = postSerive.getPost(saved.id)
            then("게시글의 내용이 정상적으로 반환됨을 확인한다.") {
                post.id shouldBe saved.id
                post.title shouldBe saved.title
                post.content shouldBe saved.content
                post.createdBy shouldBe saved.createdBy
                post.createdAt shouldBe saved.createdAt
            }
            then("태그가 정상적으로 조회됨을 확인한다."){
                post.tags.size shouldBe 2
                post.tags[0] shouldBe "태그1"
                post.tags[1] shouldBe "태그2"
            }
            then("좋아요 개수가 정상적으로 조회됨을 확인한다."){
                post.likeCount shouldBe 3
            }
        }
        `when`("게시글이 없을 때") {
            then("게시글을 찾을 수 없습니다 예외가 발생한다.") {
                shouldThrow<PostNotFoundException> {
                    postSerive.getPost(0L)
                }
            }
        }
        `when`("댓글 추가시") {
            val comment1 = Comment(
                content = "댓글 내용1",
                post = saved,
                createdBy = "작성자"
            )
            val comment2 = Comment(
                content = "댓글 내용2",
                post = saved,
                createdBy = "작성자"
            )
            val comment3 = Comment(
                content = "댓글 내용3",
                post = saved,
                createdBy = "작성자"
            )

            commentRepository.save(comment1)
            commentRepository.save(comment2)
            commentRepository.save(comment3)
            then("댓글이 함께 조회됨을 확인한다.") {
                val post = postSerive.getPost(saved.id)
                post.comments.size shouldBe 3
                post.comments[0].id shouldBe comment1.id
                post.comments[1].id shouldBe comment2.id
                post.comments[2].id shouldBe comment3.id
            }
        }
    }
    given("게시글 목록조회시") {
        `when`("정상 조회시") {
            val postPage = postSerive.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto())
            then("게시글 페이지가 반환된다.") {
                postPage.number shouldBe 0
                postPage.size shouldBe 5
                postPage.content.size shouldBe 5
            }
        }
        `when`("타이틀로 검색") {
            then("타이틀에 해당하는 게시글이 반환된다.") {
                val postPage = postSerive.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(title = "제목2"))
                postPage.number shouldBe 0
                postPage.size shouldBe 5
                postPage.content.size shouldBe 1
            }
        }
        `when`("작성자로 검색") {
            then("작성자에 해당하는 게시글이 반환된다.") {
                val postPage = postSerive.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(createdBy = "작성자"))
                postPage.number shouldBe 0
                postPage.size shouldBe 5
                postPage.content.size shouldBe 5
            }
            then("첫번쨰 태그가 조회됨을 확인한다."){
                val postPage = postSerive.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(createdBy = "작성자"))
                postPage.content.forEach {
                    it.tag shouldBe "태그1"
                }
            }
        }
        `when`("태그로 검색"){
            then("태그에 해당하는 게시글이 반환된다."){
                val postPage = postSerive.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(tag = "태그3"))
                postPage.number shouldBe 0
                postPage.size shouldBe 5
                postPage.content.size shouldBe 3
            }
        }
        `when`("좋아요가 추가됐을 떄"){
            val postId = postSerive.createPost(
                PostCreateRequestDto(
                    title = "제목",
                    content = "내용",
                    createdBy = "작성자",
                    tags = listOf("태그5")
                )
            )
            likeService.createLike(postId, "sim")
            then("좋아요 개수가 정상적으로 조회됨을 확인한다."){
                val postPage =
                    postSerive.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(tag="태그5"))
                postPage.content[0].likeCount shouldBe 1
            }
        }
    }
})
