package com.study.simplefile

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@RestController
@RequestMapping("/simple/file")
class FileController(
    private val fileService: FileService
) {
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    fun save(
        @RequestParam("img") img: MultipartFile
    ): ResponseEntity<String> {
        return try {
            val imgPath = fileService.save(img)

            ResponseEntity.ok().body(imgPath)
        } catch (e: IOException) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image")
        }
    }
}