package com.example.simplecrud.application.controllers

import com.example.simplecrud.domain.post.dto.PostCreateRequestDto
import com.example.simplecrud.domain.post.dto.PostDto
import com.example.simplecrud.domain.post.PostService
import com.example.simplecrud.domain.post.dto.PostUpdateRequestDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {
    @GetMapping
    fun getPosts(): ResponseEntity<List<PostDto>> {
        return ResponseEntity.ok(postService.getPosts())
    }

    @PostMapping
    fun save(@RequestBody postCreateRequestDto: PostCreateRequestDto): ResponseEntity<PostDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(postCreateRequestDto))
    }

    @GetMapping("{postId}")
    fun getPostById(@PathVariable postId: Long): ResponseEntity<PostDto> {
        return ResponseEntity.ok(postService.getPostById(postId))
    }

    @PatchMapping("{postId}")
    fun updateById(
        @PathVariable postId: Long,
        @RequestBody postUpdateRequestDto: PostUpdateRequestDto
    ): ResponseEntity.HeadersBuilder<*> {
        postService.updateById(postId, postUpdateRequestDto)

        return ResponseEntity.noContent()
    }

    @DeleteMapping("{postId}")
    fun deleteById(@PathVariable postId: Long): ResponseEntity.HeadersBuilder<*> {
        postService.deleteById(postId)

        return ResponseEntity.noContent()
    }
}