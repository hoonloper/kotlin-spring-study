package com.example.simpleredis.scenario

import com.example.simpleredis.dto.TicketApplyDto
import com.example.simpleredis.dto.TicketDto
import com.example.simpleredis.entity.ApplyEntity
import com.example.simpleredis.entity.TicketEntity
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.concurrent.Executors
import java.util.concurrent.Future

@SpringBootTest
@AutoConfigureMockMvc
class TicketConcurrentRequestTest(
    @Autowired private val mvc: MockMvc,
) {
    val objectMapper: ObjectMapper = ObjectMapper()
    lateinit var ticket: TicketEntity

    @BeforeEach
    fun init() {
        objectMapper.registerModule(JavaTimeModule())

        // 새로운 Ticket 발행 ---------------------------------------------
        val ticketDto = TicketDto(currentCount = 1, maxCount = 5)
        val serializedTicketDto = objectMapper.writeValueAsString(ticketDto)

        mvc.perform(post("/tickets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(serializedTicketDto)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated)

        // Ticket 정상 발행 여부 확인 ---------------------------------------------
        val responseTickets = mvc.perform(get("/tickets")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andReturn().response
        val responseTicketsBody = responseTickets.contentAsString

        val ticketsType = object : TypeReference<List<TicketEntity>>() {}
        val tickets: List<TicketEntity> = objectMapper.readValue(responseTicketsBody, ticketsType)
        val createdTicket = tickets.first()

        assertThat(createdTicket.currentCount).isEqualTo(ticketDto.currentCount)
        assertThat(createdTicket.maxCount).isEqualTo(ticketDto.maxCount)

        // 예매 테스트를 위한 Ticket 정보 저장 ---------------------------------------------
        ticket = createdTicket
    }

    @Test
    fun `티켓 동시 요청 테스트`() {
        // 10번의 요청을 위한 상수 ---------------------------------------------
        val maxLoop = 10

        // 동시 요청을 위한 Executors 정의 및 할당 ---------------------------------------------
        val executorService = Executors.newFixedThreadPool(maxLoop)
        val futures = mutableListOf<Future<Unit>>()
        repeat(maxLoop) { index ->
            val future = executorService.submit<Unit> {
                val applyDto = TicketApplyDto(userId = (index + 1).toLong(), ticketId = ticket.id!!)
                val serializedTicketApplyDto = objectMapper.writeValueAsString(applyDto)

                // 품절되거나 이미 예매가 된 Ticket이더라도 예외를 무시하기 위한 try - catch
                try {
                    mvc.perform(post("/tickets/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializedTicketApplyDto)
                        .accept(MediaType.APPLICATION_JSON)
                    )
                } catch (ex: Exception) {
                    println("Error occurred: ${ex.message}")
                }
            }

            futures.add(future)
        }

        futures.forEach { it.get() } // 모든 Future 객체의 작업이 완료될 때까지 기다림
        executorService.shutdown()

        // 동시 요청 이후 개수 비교를 위한 예매 내역 조회 ---------------------------------------------
        val responseAppliedTickets = mvc.perform(get("/tickets/applied")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andReturn().response
        val responseAppliedTicketsBody = responseAppliedTickets.contentAsString

        val appliedTicketsType = object : TypeReference<List<ApplyEntity>>() {}
        val appliedTickets = objectMapper.readValue(responseAppliedTicketsBody, appliedTicketsType)

        val appliedTicketCount = appliedTickets.count()

        // 발행한 Ticket 최대 개수와 예매된 티켓의 개수 비교 ---------------------------------------------
        assertThat(appliedTicketCount.toLong()).isEqualTo(ticket.maxCount)
    }
}