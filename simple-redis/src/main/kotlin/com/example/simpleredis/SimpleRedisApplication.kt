package com.example.simpleredis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleRedisApplication

fun main(args: Array<String>) {
    runApplication<SimpleRedisApplication>(*args)
}
