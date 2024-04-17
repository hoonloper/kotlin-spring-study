package com.study.simplefile

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDateTime

const val UPLOAD_ROOT_PATH = "uploads"

@Service
class FileService(
    private val fileRepository: FileRepository
) {
    fun save(img: MultipartFile): String {
        // 파일 이름을 임의로 생성합니다. 중복을 피하기 위해 UUID를 사용할 수도 있습니다.
        val imgName = getImgName(img)

        val directory = getDirectory()
        val imgPath = File(directory.absolutePath + File.separator + imgName)

        img.transferTo(imgPath)

        val fileEntity = FileEntity(
            name = imgName,
            path = imgPath.path,
            size = img.size,
            createdDate = LocalDateTime.now()
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
}