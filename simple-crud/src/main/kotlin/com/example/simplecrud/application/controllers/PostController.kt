package com.example.simplecrud.application.controllers

import com.example.simplecrud.domain.post.PostCreateRequestDto
import com.example.simplecrud.domain.post.PostDto
import com.example.simplecrud.domain.post.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {
    @GetMapping
    fun getPosts(): ResponseEntity<Any> {
        return ResponseEntity.ok(postService.getPosts())
    }

    @PostMapping
    fun save(@RequestBody postCreateRequestDto: PostCreateRequestDto): ResponseEntity<PostDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(postCreateRequestDto))
    }
}