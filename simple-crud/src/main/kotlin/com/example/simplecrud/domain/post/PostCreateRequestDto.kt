package com.example.simplecrud.domain.post

import lombok.Getter

@Getter
class PostCreateRequestDto(
    val title: String,
    val description: String,
)