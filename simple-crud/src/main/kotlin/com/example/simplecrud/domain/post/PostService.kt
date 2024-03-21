package com.example.simplecrud.domain.post

import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {
    fun getPosts(): List<Post> {
        return postRepository.findAll()
    }
    fun save(postCreateRequestDto: PostCreateRequestDto): PostDto {
        val post = postRepository.save(
            Post(title = postCreateRequestDto.title, description = postCreateRequestDto.description)
        )

        return PostDto(post)
    }
}