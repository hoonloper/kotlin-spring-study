package com.study.simplefile

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.Instant
import java.time.ZoneId

const val UPLOAD_ROOT_PATH = "uploads"

@Service
class FileService(
    private val fileRepository: FileRepository
) {
    fun getAllImages(): MutableList<FileEntity> = fileRepository.findAll()

    fun save(img: MultipartFile): String {
        val directory = getDirectory()
        val imgName = getImgName(img)
        val imgPath = File(directory.absolutePath + File.separator + imgName)

        img.transferTo(imgPath)

        val fileEntity = FileEntity(
            name = imgName,
            path = imgPath.path,
            size = img.size,
            createdDate = getCreatedDateFromImg(imgPath).atZone(ZoneId.systemDefault())
        )

        fileRepository.save(fileEntity)

        return imgPath.path
    }

    private fun getDirectory(): File {
        val directory = File(UPLOAD_ROOT_PATH)

        if (!directory.exists()) {
            directory.mkdir()
        }

        return directory
    }

    private fun getImgName(img: MultipartFile) = "${System.currentTimeMillis()}_${img.originalFilename}"

    private fun getCreatedDateFromImg(imgPath: File) = Instant.ofEpochMilli(imgPath.lastModified())
}