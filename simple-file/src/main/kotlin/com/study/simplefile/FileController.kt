package com.study.simplefile

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/simple/file")
class FileController(
    private val fileService: FileService
) {
    @PostMapping
    fun save() {
        fileService.save()
    }
}