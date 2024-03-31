package com.example.simpleredis.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var name: String? = null,

    @Column
    var email: String? = null,

    @Column
    var age: Long? = null,
): BaseEntity()