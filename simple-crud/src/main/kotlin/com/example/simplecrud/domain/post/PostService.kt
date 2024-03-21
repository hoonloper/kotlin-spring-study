package com.example.simplecrud.domain.post

import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {
    fun getPosts(): List<PostDto> {
        val posts = postRepository.findAll()

        return posts.map { PostDto(it) }
    }

    fun save(postCreateRequestDto: PostCreateRequestDto): PostDto {
        val post = postRepository.save(
            Post(title = postCreateRequestDto.title, description = postCreateRequestDto.description)
        )

        return PostDto(post)
    }
}