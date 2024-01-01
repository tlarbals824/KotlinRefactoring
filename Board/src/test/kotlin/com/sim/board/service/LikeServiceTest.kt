package com.sim.board.service

import com.sim.board.domain.Post
import com.sim.board.exception.PostNotFoundException
import com.sim.board.repository.LikeRepository
import com.sim.board.repository.PostRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class LikeServiceTest(
    private val likeService: LikeService,
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository
) : BehaviorSpec({
    given("좋아요 생성시"){
        val saved = postRepository.save(
            Post(
                title = "title",
                content = "content",
                createBy = "sim",
                tags = listOf("tag1", "tag2")
            )
        )
        `when`("요청이 정상적으로 들어오면"){
            val likeId = likeService.createLike(1L, "sim")
            then("좋아요가 정상적으로 생성됨을 확인한다."){
                val like = likeRepository.findByIdOrNull(likeId)
                like shouldNotBe null
                like?.createdBy shouldBe "sim"
            }
        }
        `when`("게시글이 존재하지 않으면"){
            then("존재하지 않는 게시글 예외가 발생한다."){
                val exception = shouldThrow<PostNotFoundException> {
                    likeService.createLike(100L, "sim")
                }
            }
        }
    }
}){
}