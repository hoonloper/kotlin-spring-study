package com.example.simpleredis.service

import com.example.simpleredis.dto.TicketDto
import com.example.simpleredis.entity.ApplyEntity
import com.example.simpleredis.entity.TicketEntity
import com.example.simpleredis.repository.ApplyJpaRepository
import com.example.simpleredis.repository.TicketJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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

    @Transactional
    fun applyTicket(userId: Long, ticketId: Long) {
        if (applyJpaRepository.existsByUserIdAndTicketId(userId, ticketId)) {
            throw Exception("이미 예매된 티켓")
        }

        val ticket = ticketJpaRepository.findByIdOrNull(ticketId) ?: throw Exception("티켓 없음")
        if (ticket.isDone()) {
            throw Exception("품절됨")
        }

        ticket.increaseCount()

        ticketJpaRepository.save(ticket)
        applyJpaRepository.save(ApplyEntity(userId = userId, ticketId = ticketId))
    }
}