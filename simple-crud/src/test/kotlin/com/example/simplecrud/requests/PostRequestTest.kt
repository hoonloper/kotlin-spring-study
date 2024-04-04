package com.example.simplecrud.requests

import com.example.simplecrud.domain.post.dto.PostCreateRequestDto
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional


@Transactional
@SpringBootTest
class PostRequestTest(
    @MockBean
    private val mockMvc: MockMvc,
): DescribeSpec({
    describe("POST /posts") {
        context("Body를 포함해 요청을 보내면") {
            it("정상적으로 저장된다.") {
                val postCreateRequestDto = PostCreateRequestDto(title = "제목", description = "내용")

                val objectMapper = ObjectMapper()
                val body = objectMapper.writeValueAsString(postCreateRequestDto)

                mockMvc.perform(
                    post("/posts")
                    .content(body)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                    .andExpect(status().isCreated)
            }
        }
    }
})