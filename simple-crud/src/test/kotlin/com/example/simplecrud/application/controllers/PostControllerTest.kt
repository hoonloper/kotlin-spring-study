package com.example.simplecrud.application.controllers

import com.example.simplecrud.domain.post.PostCreateRequestDto
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
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
}) {
}