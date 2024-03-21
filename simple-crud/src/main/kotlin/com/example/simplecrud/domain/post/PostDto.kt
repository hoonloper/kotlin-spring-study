package com.example.simplecrud.domain.post

import lombok.Getter
import java.time.LocalDateTime

@Getter
class PostDto(
    private val id: Long,
    private val title: String,
    private val description: String,
    private val createdAt: LocalDateTime,
    private val updatedAt: LocalDateTime
) {
    fun getId(): Long {
        return this.id
    }
    fun getTitle(): String {
        return this.title
    }
    fun getDescription(): String {
        return this.description
    }
    fun getCreatedAt(): LocalDateTime {
        return this.createdAt
    }
    fun getUpdatedAt(): LocalDateTime {
        return this.updatedAt
    }

    constructor(post: Post) : this(
        post.id!!,
        post.title,
        post.description,
        post.createdAt,
        post.updatedAt
    )
}