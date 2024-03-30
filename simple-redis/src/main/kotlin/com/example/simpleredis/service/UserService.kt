package com.example.simpleredis.service

import com.example.simpleredis.entity.User
import com.example.simpleredis.repository.UserRedisRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRedisRepository: UserRedisRepository
) {
    fun getUsers(): MutableIterable<User> {
        return userRedisRepository.findAll()
    }
}