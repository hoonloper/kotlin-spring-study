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

    describe("생성") {
        context("Post를 생성하면") {
            it("정상적으로 저장된다.") {
                val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")
                val response = postController.save(postCreateRequestDto)
                val postDto = response.body

                postDto shouldNotBe null

                if (postDto != null) {
                    postDto.getId() shouldNotBe null
                    postDto.getTitle() shouldBe postCreateRequestDto.title
                    postDto.getDescription() shouldBe postCreateRequestDto.description
                    postDto.getCreatedAt() shouldBeBefore LocalDateTime.now()
                    postDto.getUpdatedAt() shouldBeBefore LocalDateTime.now()
                }
            }
        }
    }
}) {
}