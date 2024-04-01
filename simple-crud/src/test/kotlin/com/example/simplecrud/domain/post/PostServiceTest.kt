package com.example.simplecrud.domain.post

import com.example.simplecrud.domain.post.dto.PostCreateRequestDto
import com.example.simplecrud.domain.post.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.date.shouldBeBefore
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Transactional
@SpringBootTest
class PostServiceBehaviorTest(
    private val postService: PostService
): BehaviorSpec({
    given("조회") {
        `when`("Post가 존재하면") {
            val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")
            val postCreateRequestDtoList = List(5) {
                postService.save(postCreateRequestDto)
            }

            then("Post 목록을 응답한다.") {
                val posts = postService.getPosts()

                posts.forEachIndexed { index, post ->
                    val expectedPost = postCreateRequestDtoList[index]

                    expectedPost shouldNotBe null

                    post.id shouldBe expectedPost.id
                    post.title shouldBe expectedPost.title
                    post.description shouldBe expectedPost.description
                    post.createdAt.isEqual(expectedPost.createdAt) shouldBe true
                    post.updatedAt.isEqual(expectedPost.updatedAt) shouldBe true
                }
            }
        }
    }
})

@Transactional
@SpringBootTest
class PostServiceTest (
    private val postService: PostService
): DescribeSpec({
    extension(SpringExtension)

    describe("조회") {
        context("Post 전체를 요청하면") {
            it("정상적으로 조회한다.") {
                val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")
                val postCreateRequestDtoList = List(5) {
                    postService.save(postCreateRequestDto)
                }

                val posts = postService.getPosts()

                posts.forEachIndexed { index, post ->
                    val expectedPost = postCreateRequestDtoList[index]

                    expectedPost shouldNotBe null

                    post.id shouldBe expectedPost.id
                    post.title shouldBe expectedPost.title
                    post.description shouldBe expectedPost.description
                    post.createdAt.isEqual(expectedPost.createdAt) shouldBe true
                    post.updatedAt.isEqual(expectedPost.updatedAt) shouldBe true
                }
            }
        }
        context("Post 하나를 요청하면") {
            it("정상적으로 조회한다.") {
                val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")
                val expectedPost = postService.save(postCreateRequestDto)

                val post = postService.getPostById(expectedPost.id)

                expectedPost shouldNotBe null

                expectedPost.id shouldBe post.id
                expectedPost.title shouldBe post.title
                expectedPost.description shouldBe post.description
                expectedPost.createdAt.isEqual(post.createdAt) shouldBe true
                expectedPost.updatedAt.isEqual(post.updatedAt) shouldBe true
            }
        }
    }

    describe("저장") {
        context("Post를 저장하면") {
            it("정상적으로 저장된다.") {
                val postDto = PostCreateRequestDto(title = "제목", description = "내용")
                val expectedPostDto = postService.save(postDto)

                expectedPostDto shouldNotBe null

                expectedPostDto.id shouldBeGreaterThan 0
                expectedPostDto.title shouldBe postDto.title
                expectedPostDto.description shouldBe postDto.description
                expectedPostDto.createdAt shouldBeBefore LocalDateTime.now()
                expectedPostDto.updatedAt shouldBeBefore LocalDateTime.now()
            }
        }
    }

    describe("수정") {
        context("Post를 수정하면") {
            it("정상적으로 수정된다.") {
                val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")
                val post = postService.save(postCreateRequestDto)
                val postId = post.id
                val foundPost = postService.getPostById(postId)
                val postUpdateRequestDto = PostUpdateRequestDto(title = foundPost.title, description = foundPost.description)

                postService.updateById(postId, postUpdateRequestDto)

                val updatedPost = postService.getPostById(postId)

                updatedPost shouldNotBe null

                updatedPost.id shouldBe postId
                updatedPost.title shouldBe postUpdateRequestDto.title
                updatedPost.description shouldBe postUpdateRequestDto.description
                updatedPost.createdAt shouldBe post.createdAt
                updatedPost.updatedAt shouldBeAfter post.updatedAt
            }
        }
    }

    describe("삭제") {
        context("Post를 삭제하면") {
            it("정상적으로 삭제된다.") {
                val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")
                val post = postService.save(postCreateRequestDto)
                val postId = post.id

                postService.deleteById(postId)

                // TODO: 커스텀 exception으로 교체
                val exception = shouldThrow<NoSuchElementException> {
                    postService.getPostById(postId)
                }

                exception.message shouldBe "No value present"
            }
        }
    }
})