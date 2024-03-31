package com.example.simpleredis.service

import com.example.simpleredis.repository.ApplyJpaRepository
import com.example.simpleredis.repository.TicketJpaRepository
import com.example.simpleredis.repository.UserJpaRepository
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class TicketService(
    private val userJpaRepository: UserJpaRepository,
    private val ticketJpaRepository: TicketJpaRepository,
    private val applyJpaRepository: ApplyJpaRepository
) {
}