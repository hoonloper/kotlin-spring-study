package com.example.simplecrud.domain.post.dto

import lombok.Getter

@Getter
class PostCreateRequestDto(
    val title: String,
    val description: String,
)