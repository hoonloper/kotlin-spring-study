package com.example.simplecrud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableJpaAuditing
class SimpleCrudApplication

fun main(args: Array<String>) {
    runApplication<SimpleCrudApplication>(*args)
}
