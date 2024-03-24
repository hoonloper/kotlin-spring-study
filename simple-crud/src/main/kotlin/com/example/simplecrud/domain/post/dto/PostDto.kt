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
        post.id!!,
        post.title,
        post.description,
        post.createdAt,
        post.updatedAt
    )
}