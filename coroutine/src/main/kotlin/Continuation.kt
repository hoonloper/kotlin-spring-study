import kotlinx.coroutines.delay
import kotlin.coroutines.CoroutineContext

class Continuation

/**
 * Continuation Passing Style(CPS)
 *
 */

suspend fun main() {
    val service = UserService()
    println(service.findUser(1L, null))
}

// 실제 Continuation 인터페이스
interface OriginalContinuationInterface<in T> {
// interface Continuation<in T> {
    public val context: CoroutineContext

    public fun resumeWith(result: Result<T>)
}

interface ContinuationI {
    suspend fun resumeWith(data: Any?)
}

class UserService {
    private val userProfileRepository = UserProfileRepository()
    private val userImageRepository = UserImageRepository()

    private abstract class FindUserContinuation() : ContinuationI {
        var label = 0
        var profile: Profile? = null
        var image: Image? = null
    }

    suspend fun findUser(
        userId: Long,
        continuationI: ContinuationI?,
    ): UserDto {
        val sm =
            continuationI as? FindUserContinuation ?: object : FindUserContinuation() {
                override suspend fun resumeWith(data: Any?) {
                    when (label) {
                        0 -> {
                            profile = data as Profile
                            label = 1
                        }
                        1 -> {
                            image = data as Image
                            label = 2
                        }
                    }
                    findUser(userId, this)
                }
            }

        when (sm.label) {
            0 -> {
                println("프로필을 가져오겠습니다.")
                userProfileRepository.findProfile(userId, sm)
            }
            1 -> {
                println("이미지를 가져오겠습니다.")
                userImageRepository.findImage(sm.profile!!, sm)
            }
        }

        return UserDto(sm.profile!!, sm.image!!)

//        when (sm.label) {
//            0 -> { // 0단계 - 초기 시작
//                println("프로필을 가져오겠습니다.")
//                sm.label = 1
//                val profile = userProfileRepository.findProfile(userId, sm)
//                sm.profile = profile!!
//            }
//            1 -> { // 1단계 - 1차 중단 후 재시작
//                println("이미지를 가져오겠습니다.")
//                sm.label = 2
//                val image = userImageRepository.findImage(sm.profile!!, sm)
//                sm.image = image!!
//            }
//            2 -> { // 2단계 - 2차 중단 후 재시작
//                return UserDto(sm.profile!!, sm.image!!)
//            }
//        }

//        // 0단계 - 초기 시작
//        println("프로필을 가져오겠습니다.")
//        val profile = userProfileRepository.findProfile(userId)
//
//        // 1단계 - 1차 중단 후 재시작
//        println("이미지를 가져오겠습니다.")
//        val image = userImageRepository.findImage(profile)
//
//        // 2단계 - 2차 중단 후 재시작
//        return UserDto(profile, image)
    }
}

data class UserDto(
    private val profile: Profile,
    private val image: Image,
)

class UserProfileRepository {
    suspend fun findProfile(
        userId: Long,
        continuationI: ContinuationI,
    ) {
        delay(100L)

        continuationI.resumeWith(Profile())
    }
}

class Profile

class UserImageRepository {
    suspend fun findImage(
        profile: Profile,
        continuationI: ContinuationI,
    ) {
        delay(100L)

        continuationI.resumeWith(Image())
    }
}

class Image
