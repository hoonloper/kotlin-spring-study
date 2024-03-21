package com.example.simplecrud.domain.post

import com.example.simplecrud.domain.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "posts")
class Post (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column
    var title: String,
    @Column
    var description: String,
): BaseEntity() {}