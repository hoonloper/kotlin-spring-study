package com.study.simplefile

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class SimpleFileApplication

fun main(args: Array<String>) {
    runApplication<SimpleFileApplication>(*args)
}
