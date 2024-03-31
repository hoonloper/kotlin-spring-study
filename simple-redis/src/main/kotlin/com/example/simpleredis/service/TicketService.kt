package com.example.simpleredis.service

import com.example.simpleredis.dto.TicketDto
import com.example.simpleredis.entity.ApplyEntity
import com.example.simpleredis.entity.TicketEntity
import com.example.simpleredis.repository.ApplyJpaRepository
import com.example.simpleredis.repository.TicketJpaRepository
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class TicketService(
    private val ticketJpaRepository: TicketJpaRepository,
    private val applyJpaRepository: ApplyJpaRepository
) {
    fun getTickets(): List<TicketEntity> {
        return ticketJpaRepository.findAll()
    }

    fun getAppliedTickets(): List<ApplyEntity> {
        return applyJpaRepository.findAll()
    }

    fun addTicket(ticketDto: TicketDto) {
        ticketJpaRepository.save(TicketEntity(currentCount = ticketDto.currentCount, maxCount = ticketDto.maxCount))
    }

    fun applyTicket(userId: Long, ticketId: Long) {
        if (applyJpaRepository.existsByUserIdAndTicketId(userId, ticketId)) {
            throw Exception("이미 예매된 티켓")
        }
        val ticket = ticketJpaRepository.findById(ticketId).orElseThrow()
        if (ticket.isDone()) {
            throw Exception("품절됨")
        }

        ticket.increaseCount()

        applyJpaRepository.save(ApplyEntity(userId = userId, ticketId = ticketId))
    }
}