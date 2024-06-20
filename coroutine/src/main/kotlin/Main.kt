import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun main(): Unit = runBlocking { // runBlocking을 사용하는 순간 코루틴이 만들어진다.
    printWithThread("START")

    launch { // 반환값이 없는 코루틴을 만든다.
        newRoutine()
    }
    yield()

    printWithThread("END")
}

/**
 * 다른 suspend fun을 호출할 수 있다.
 *
 * [메모리 관점]
 * 새로운 루틴이 호출된 후 완전히 종료되기 전,
 * 해당 루틴에서 사용했던 정보들을 보관하고 있어야 한다.
 *
 * 루틴이 중단(yield)되었다가 해당 메모리에 접근이 가능하다.
 */
suspend fun newRoutine() {
    val num1 = 1
    val num2 = 2
    yield() // 지금 코루틴을 중단하고 다른 코루틴이 실행되도록 스레드를 양보한다.
    printWithThread("${num1 + num2}")
}

/**
 * 애플리케이션 실행 edit configurations -> VM options '-Dkotlinx.coroutiens.debug' 옵션을 주면 디버그 모드이다.
 */
fun printWithThread(str: Any) {
    println("[${Thread.currentThread().name}] $str")
}
