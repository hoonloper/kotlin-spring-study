package more

import kotlin.system.measureTimeMillis

class Tip

fun main() {
    repeat(3) {
        println("Hello World")
    }
    // TODO
    // TODO("메인 함수 구현")

    val a = 1
    val b = 2

    val start = System.currentTimeMillis()
    val result = a + b
    println("result ${System.currentTimeMillis() - start}")

    val timeMillis = measureTimeMillis {
        val result = a + b
    }
    println(timeMillis)

    val result2: Result<Int> = runCatching { 1 / 0 }
}

// 파라미터 검증
fun acceptOnlyTwo(num: Int) {
    require(num == 2) { "2만 허용" }

    if (num != 2) {
        throw IllegalArgumentException("2만 허용")
    }
}

class Person {
    val status: PersonStatus = PersonStatus.PLAYING

    // 필드, 상태 검증
    fun sleep() {
        check(this.status == PersonStatus.PLAYING) { "에러 메시지!" }

        if (this.status != PersonStatus.PLAYING) {
            throw IllegalStateException()
        }
    }

    enum class PersonStatus {
        PLAYING, SLEEPING
    }
}
