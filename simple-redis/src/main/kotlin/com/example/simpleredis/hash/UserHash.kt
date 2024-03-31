package com.example.simpleredis.hash

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("user")
class UserHash (
    @Id
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val age: String? = null,
)