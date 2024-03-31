package com.example.simpleredis.controller

import com.example.simpleredis.dto.TicketApplyDto
import com.example.simpleredis.service.TicketService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tickets")
class TicketController(
    private val ticketService: TicketService
) {
    @PostMapping
    fun applyTicket(@RequestBody ticketApplyDto: TicketApplyDto) {
        ticketService.applyTicket(ticketApplyDto.userId, ticketApplyDto.ticketId)
    }
}