package com.example.simpleredis.repository

import com.example.simpleredis.entity.TicketEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TicketJpaRepository: JpaRepository<TicketEntity, Long> {
}