package com.example.simplecrud.domain.post

import com.example.simplecrud.domain.post.dto.PostCreateRequestDto
import com.example.simplecrud.domain.post.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.date.shouldBeBefore
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


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

                    post.getId() shouldBe expectedPost.getId()
                    post.getTitle() shouldBe expectedPost.getTitle()
                    post.getDescription() shouldBe expectedPost.getDescription()
                    post.getCreatedAt().isEqual(expectedPost.getCreatedAt()) shouldBe true
                    post.getUpdatedAt().isEqual(expectedPost.getUpdatedAt()) shouldBe true
                }
            }
        }
        context("Post 하나를 요청하면") {
            it("정상적으로 조회한다.") {
                val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")
                val expectedPost = postService.save(postCreateRequestDto)

                val post = postService.getPostById(expectedPost.getId())

                expectedPost shouldNotBe null

                expectedPost.getId() shouldBe post?.getId()
                expectedPost.getTitle() shouldBe post?.getTitle()
                expectedPost.getDescription() shouldBe post?.getDescription()
                expectedPost.getCreatedAt().isEqual(post?.getCreatedAt()) shouldBe true
                expectedPost.getUpdatedAt().isEqual(post?.getUpdatedAt()) shouldBe true
            }
        }
    }

    describe("저장") {
        context("Post를 저장하면") {
            it("정상적으로 저장된다.") {
                val postDto = PostCreateRequestDto(title = "제목", description = "내용")
                val expectedPostDto = postService.save(postDto)

                expectedPostDto shouldNotBe null

                expectedPostDto.getId() shouldBeGreaterThan 0
                expectedPostDto.getTitle() shouldBe postDto.title
                expectedPostDto.getDescription() shouldBe postDto.description
                expectedPostDto.getCreatedAt() shouldBeBefore LocalDateTime.now()
                expectedPostDto.getUpdatedAt() shouldBeBefore LocalDateTime.now()
            }
        }
    }

    describe("수정") {
        context("Post를 수정하면") {
            it("정상적으로 수정된다.") {
                val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")
                val post = postService.save(postCreateRequestDto)
                val postId = post.getId()
                val foundPost = postService.getPostById(postId)
                val postUpdateRequestDto = PostUpdateRequestDto(title = foundPost.getTitle(), description = foundPost.getDescription())

                postService.updateById(postId, postUpdateRequestDto)

                val updatedPost = postService.getPostById(postId)

                updatedPost shouldNotBe null

                updatedPost.getId() shouldBe postId
                updatedPost.getTitle() shouldBe postUpdateRequestDto.title
                updatedPost.getDescription() shouldBe postUpdateRequestDto.description
                updatedPost.getCreatedAt() shouldBe post.getCreatedAt()
                updatedPost.getUpdatedAt() shouldBeAfter post.getUpdatedAt()
            }
        }
    }

    describe("삭제") {
        context("Post를 삭제하면") {
            it("정상적으로 삭제된다.") {
                val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")
                val post = postService.save(postCreateRequestDto)
                val postId = post.getId()

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