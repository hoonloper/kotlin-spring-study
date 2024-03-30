package com.example.simpleredis.repository

import com.example.simpleredis.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRedisRepository: CrudRepository<User, Long> {
}