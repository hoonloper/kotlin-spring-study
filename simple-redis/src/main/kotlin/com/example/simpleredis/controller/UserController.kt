package com.example.simpleredis.controller

import com.example.simpleredis.hash.UserHash
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
    fun getUsers(): MutableIterable<UserHash> {
        return userService.getUsers()
    }

    @PostMapping
    fun saveUser(@RequestBody user: UserHash) {
        userService.saveUser(user)
    }
}