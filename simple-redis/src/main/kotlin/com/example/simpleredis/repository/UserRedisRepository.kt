package com.example.simpleredis.repository

import com.example.simpleredis.hash.UserHash
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRedisRepository: CrudRepository<UserHash, Long> {
}