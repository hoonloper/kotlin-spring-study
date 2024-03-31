package com.example.simpleredis.controller

import com.example.simpleredis.dto.TicketApplyDto
import com.example.simpleredis.dto.TicketDto
import com.example.simpleredis.entity.ApplyEntity
import com.example.simpleredis.entity.TicketEntity
import com.example.simpleredis.service.TicketService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tickets")
class TicketController(
    private val ticketService: TicketService
) {
    @GetMapping
    fun getTickets(): List<TicketEntity> {
        return ticketService.getTickets()
    }

    @GetMapping("/applied")
    fun getAppliedTickets(): List<ApplyEntity> {
        return ticketService.getAppliedTickets()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addTicket(@RequestBody ticketDto: TicketDto) {
        ticketService.addTicket(ticketDto)
    }

    @PostMapping("/apply")
    @ResponseStatus(HttpStatus.CREATED)
    fun applyTicket(@RequestBody ticketApplyDto: TicketApplyDto) {
        ticketService.applyTicket(ticketApplyDto.userId, ticketApplyDto.ticketId)
    }
}