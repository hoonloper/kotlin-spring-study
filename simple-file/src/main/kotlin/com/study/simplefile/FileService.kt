package com.study.simplefile

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class FileService(
    private val fileRepository: FileRepository
) {
    fun save() {
        val fileEntity = FileEntity(
            name = "",
            path = "",
            size = 0L,
            createdDate = LocalDateTime.now()
        )

        fileRepository.save(fileEntity)
    }
}