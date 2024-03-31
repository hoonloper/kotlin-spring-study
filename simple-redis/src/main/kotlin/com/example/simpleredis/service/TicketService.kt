package com.example.simpleredis.service

import com.example.simpleredis.dto.TicketDto
import com.example.simpleredis.entity.ApplyEntity
import com.example.simpleredis.entity.TicketEntity
import com.example.simpleredis.repository.ApplyJpaRepository
import com.example.simpleredis.repository.TicketJpaRepository
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.Exception
import java.util.concurrent.TimeUnit

@Service
class TicketService(
    private val ticketJpaRepository: TicketJpaRepository,
    private val applyJpaRepository: ApplyJpaRepository,
    private val redissonClient: RedissonClient
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
        val rLock = redissonClient.getLock(ticketId.toString()) // Lock 호출

        val locked = rLock.tryLock(5, 1, TimeUnit.SECONDS) // Lock 획득 시도
        if (!locked) {
            throw Exception("Lock 획득 실패")
        }

        if (applyJpaRepository.existsByUserIdAndTicketId(userId, ticketId)) {
            throw Exception("이미 예매된 티켓")
        }

        val ticket = ticketJpaRepository.findByIdOrNull(ticketId) ?: throw Exception("티켓 없음")
        if (ticket.isDone()) {
            throw Exception("품절됨")
        }

        try {
            ticket.increaseCount()
            ticketJpaRepository.save(ticket)

            applyJpaRepository.save(ApplyEntity(userId = userId, ticketId = ticketId))
        } catch (e: Exception) {
            throw Exception(e.message)
        } finally {
            if (rLock.isHeldByCurrentThread) {
                rLock.unlock()
            } else {
                throw Exception("Lock 소유중이 아닐 때")
            }
        }
    }
}