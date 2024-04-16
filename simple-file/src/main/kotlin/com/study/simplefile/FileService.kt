package com.study.simplefile

import org.springframework.stereotype.Service

@Service
class FileService(
    private val fileRepository: FileRepository
) {
}