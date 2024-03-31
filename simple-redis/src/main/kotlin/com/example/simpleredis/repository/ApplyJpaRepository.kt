package com.example.simpleredis.repository

import com.example.simpleredis.entity.ApplyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ApplyJpaRepository: JpaRepository<ApplyEntity, Long>  {
    fun existsByUserIdAndTicketId(userId: Long, ticketId: Long): Boolean
}