package com.example.simplecrud.domain.post

import com.example.simplecrud.domain.common.BaseEntity
import com.example.simplecrud.domain.post.dto.PostUpdateRequestDto
import jakarta.persistence.*
import lombok.Getter
import java.time.LocalDateTime

@Getter
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
): BaseEntity() {
    fun update(postUpdateRequestDto: PostUpdateRequestDto) {
        this.title = postUpdateRequestDto.title
        this.description = postUpdateRequestDto.description
        this.updatedAt = LocalDateTime.now()
    }
}