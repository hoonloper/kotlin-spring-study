package com.example.simpleredis.controller

import com.example.simpleredis.entity.UserEntity
import com.example.simpleredis.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getUsers(): List<UserEntity> {
        return userService.getUsers()
    }

    @PostMapping
    fun saveUser(@RequestBody user: UserEntity) {
        userService.saveUser(user)
    }
}