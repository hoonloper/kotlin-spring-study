package com.example.simplecrud.requests

import io.kotest.core.spec.style.DescribeSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional


@Transactional
@SpringBootTest
class PostRequestTest(
    @Autowired
    private val mockMvc: MockMvc

): DescribeSpec({
    describe("POST /posts") {
        context("Body를 포함해 요청을 보내면") {
            it("정상적으로 저장된다.") {
                mockMvc.perform(post("/posts")
                    .content("")
                    .contentType(MediaType.APPLICATION_JSON)
                )
                    .andExpect(status().isCreated)
            }
        }
    }
})