package com.example.simplecrud.domain.post

import com.example.simplecrud.domain.common.BaseEntity
import com.example.simplecrud.domain.post.dto.PostUpdateRequestDto
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
class Post (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var title: String,
    @Column(nullable = false)
    var description: String,
): BaseEntity() {
    fun update(postUpdateRequestDto: PostUpdateRequestDto) {
        this.title = postUpdateRequestDto.title
        this.description = postUpdateRequestDto.description
        this.updatedAt = LocalDateTime.now()
    }
}