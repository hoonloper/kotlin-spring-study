package com.example.simpleredis.entity

import jakarta.persistence.*

@Entity
@Table(name = "applies")
class ApplyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var userId: Long? = 0,

    @Column
    var ticketId: Long? = 0,
): BaseEntity()