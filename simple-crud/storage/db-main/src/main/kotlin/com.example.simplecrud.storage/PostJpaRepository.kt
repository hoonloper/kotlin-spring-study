package com.example.simplecrud.storage

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal interface PostJpaRepository: JpaRepository<Post, Long>