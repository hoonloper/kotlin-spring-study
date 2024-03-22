package com.example.simplecrud.domain.post

import com.example.simplecrud.domain.post.dto.PostCreateRequestDto
import com.example.simplecrud.domain.post.dto.PostDto
import com.example.simplecrud.domain.post.dto.PostUpdateRequestDto
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

    fun getPostById(postId: Long): PostDto {
        return PostDto(postRepository.findById(postId).orElseThrow())
    }

    fun updateById(postId: Long, postUpdateRequestDto: PostUpdateRequestDto) {
        val post = postRepository.findById(postId).orElseThrow()

        post.update(postUpdateRequestDto)

        postRepository.save(post)
    }
}