package com.example.simpleredis.controller

import com.example.simpleredis.entity.User
import com.example.simpleredis.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping()
    fun getUsers(): MutableIterable<User> {
        val users = userService.getUsers()

        return users
    }
}