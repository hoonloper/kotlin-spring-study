package com.example.simplecrud

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class PostController {
    @GetMapping
    fun getPosts(): ResponseEntity<String> {
        return ResponseEntity.ok("GET /posts")
    }
}