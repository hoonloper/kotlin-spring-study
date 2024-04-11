package com.example.simplecrud.storage

import jakarta.persistence.*

@Entity
@Table(name = "posts")
internal class Post (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var title: String,
    @Column(nullable = false)
    var description: String,
): BaseEntity()