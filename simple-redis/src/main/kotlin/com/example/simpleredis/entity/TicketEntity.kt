package com.example.simpleredis.entity

import jakarta.persistence.*

@Entity
@Table(name = "tickets")
class TicketEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var currentCount: Long? = 0,

    @Column
    var maxCount: Long? = 5
): BaseEntity() {
    fun isDone(): Boolean {
        return currentCount!! >= maxCount!!
    }

    fun increaseCount() {
        currentCount = currentCount?.plus(1)
    }
}