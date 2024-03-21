package com.example.simplecrud.domain.post

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class PostRepositoryTest (
    private val postRepository: PostRepository
): DescribeSpec({
    extension(SpringExtension)

    describe("save") {
        context("Post를 저장하면") {
            it("정상적으로 저장된다.") {
                postRepository.save(Post(title = "제목", description = "내용"))
                postRepository.findAll().size shouldBe 1
            }
        }
    }

    describe("saveAll") {
        context("Post 2개를 저장하면") {
            it("2개가 정상적으로 저장된다.") {
                postRepository.saveAll(
                    listOf(
                        Post(title = "제목", description = "내용"),
                        Post(title = "제목", description = "내용")
                    )
                )
                postRepository.findAll().size shouldBe 2
            }
        }
    }
})