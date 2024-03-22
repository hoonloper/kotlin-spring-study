package com.example.simplecrud.domain.post

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.date.shouldBeBefore
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.longs.shouldBeLessThan
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

    describe("save") {
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
})