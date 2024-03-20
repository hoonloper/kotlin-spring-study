package com.example.simplecrud.domain.post

import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {
    fun getPosts(): List<Post> {
        return postRepository.findAll()
    }
}