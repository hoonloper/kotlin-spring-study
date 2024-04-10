package com.example.simplecrud.domain.post.dto

import com.example.simplecrud.domain.post.Post
import java.time.LocalDateTime

data class PostDto(
    val id: Long,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    constructor(post: Post) : this(
        id = post.id!!,
        title = post.title,
        description = post.description,
        createdAt = post.createdAt,
        updatedAt = post.updatedAt
    )
}