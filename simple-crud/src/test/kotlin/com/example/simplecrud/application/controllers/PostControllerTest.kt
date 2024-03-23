package com.example.simplecrud.application.controllers

import com.example.simplecrud.domain.post.dto.PostCreateRequestDto
import com.example.simplecrud.domain.post.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.date.shouldBeBefore
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@SpringBootTest
class PostControllerTest(
    private val postController: PostController
): DescribeSpec({
    extension(SpringExtension)

    describe("조회") {
        context("Post 전체를 요청하면") {
            it("정상적으로 조회한다.") {
                val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")
                val postCreateRequestDtoList = List(5) {
                    postController.save(postCreateRequestDto).body
                }

                val posts = postController.getPosts().body

                posts!!.forEachIndexed { index, post ->
                    val expectedPost = postCreateRequestDtoList[index]

                    expectedPost shouldNotBe null

                    if(expectedPost != null) {
                        post.getId() shouldBe expectedPost.getId()
                        post.getTitle() shouldBe expectedPost.getTitle()
                        post.getDescription() shouldBe expectedPost.getDescription()
                        post.getCreatedAt().isEqual(expectedPost.getCreatedAt()) shouldBe true
                        post.getUpdatedAt().isEqual(expectedPost.getUpdatedAt()) shouldBe true
                    }
                }
            }
        }
        context("Post 하나를 요청하면") {
            it("정상적으로 조회한다.") {
                val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")
                val expectedPost = postController.save(postCreateRequestDto).body

                val post = expectedPost?.getId()?.let { postController.getPostById(it).body }

                expectedPost shouldNotBe null

                expectedPost?.getId() shouldBe post?.getId()
                expectedPost?.getTitle() shouldBe post?.getTitle()
                expectedPost?.getDescription() shouldBe post?.getDescription()
                expectedPost?.getCreatedAt()?.isEqual(post?.getCreatedAt()) shouldBe true
                expectedPost?.getUpdatedAt()?.isEqual(post?.getUpdatedAt()) shouldBe true
            }
        }
    }

    describe("생성") {
        context("Post를 생성하면") {
            it("정상적으로 저장된다.") {
                val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")
                val post = postController.save(postCreateRequestDto).body

                post shouldNotBe null

                if (post != null) {
                    post.getId() shouldNotBe null
                    post.getTitle() shouldBe postCreateRequestDto.title
                    post.getDescription() shouldBe postCreateRequestDto.description
                    post.getCreatedAt() shouldBeBefore LocalDateTime.now()
                    post.getUpdatedAt() shouldBeBefore LocalDateTime.now()
                }
            }
        }
    }

    describe("수정") {
        context("Post를 수정하면") {
            it("정상적으로 수정된다.") {
                val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")
                val post = postController.save(postCreateRequestDto).body ?: throw Error()
                val postId = post.getId()
                val foundPost = postController.getPostById(postId).body ?: throw Error()

                val postUpdateRequestDto = PostUpdateRequestDto(title = foundPost.getTitle(), description = foundPost.getDescription())

                postController.updateById(postId, postUpdateRequestDto)

                val updatedPost = postController.getPostById(postId).body


                updatedPost shouldNotBe null

                if (updatedPost != null) {
                    updatedPost.getId() shouldBe postId
                    updatedPost.getTitle() shouldBe postUpdateRequestDto.title
                    updatedPost.getDescription() shouldBe postUpdateRequestDto.description
                    updatedPost.getCreatedAt() shouldBe post.getCreatedAt()
                    updatedPost.getUpdatedAt() shouldBeAfter post.getUpdatedAt()
                }
            }
        }
    }

    describe("삭제") {
        context("Post를 삭제하면") {
            it("정상적으로 삭제된다.") {
                val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")
                val post = postController.save(postCreateRequestDto).body ?: throw Error()
                val postId = post.getId()

                postController.deleteById(postId)

                // TODO: 커스텀 exception으로 교체
                val exception = shouldThrow<NoSuchElementException> {
                    postController.getPostById(postId)
                }

                exception.message shouldBe "No value present"
            }
        }
    }
}) {
}