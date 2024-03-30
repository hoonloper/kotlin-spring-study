package com.example.simpleredis.entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("user")
class User (
    @Id
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val age: String? = null,
)