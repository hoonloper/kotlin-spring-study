package com.example.simplecrud.domain.post.dto

import lombok.Getter

@Getter
class PostUpdateRequestDto(
    val title: String,
    val description: String,
)