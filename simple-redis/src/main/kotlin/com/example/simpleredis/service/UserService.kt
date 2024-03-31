package com.example.simpleredis.service

import com.example.simpleredis.entity.UserEntity
import com.example.simpleredis.repository.UserJpaRepository
import com.example.simpleredis.repository.UserRedisRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRedisRepository: UserRedisRepository,
    private val userJpaRepository: UserJpaRepository
) {
    fun getUsers(): List<UserEntity> {
        return userJpaRepository.findAll()
    }

    fun saveUser(user: UserEntity) {
        userJpaRepository.save(user)
    }
}