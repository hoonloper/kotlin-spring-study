package com.example.simpleredis.service

import com.example.simpleredis.hash.UserHash
import com.example.simpleredis.repository.UserRedisRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRedisRepository: UserRedisRepository
) {
    fun getUsers(): MutableIterable<UserHash> {
        return userRedisRepository.findAll()
    }

    fun saveUser(user: UserHash) {
        userRedisRepository.save(user)
    }
}