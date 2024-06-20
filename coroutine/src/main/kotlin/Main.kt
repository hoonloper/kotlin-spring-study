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

/**
 * 프로세스가 컨텍스트 스위칭이 일어나면 모든 메모리가 교체되어 비용이 많이 발생한다.
 * 스레드가 컨텍스트 스위칭이 일어나면 힙 영역은 공유되고 스택 영역만 교체되기에 프로세스보다 비용이 적게 발생한다.
 * 코루틴이 컨텍스트 스위칭이 일어나면 힙 영역과 스택 영역 전부를 공유하기에 스레드보다 비용이 적게 발생한다.
 * 즉, 하나의 스레드에서 동시성을 확보할 수 있다.
 * 또한 코루틴은 스스로 자리를 양보할 수 있다. (비선점형이라 하며, 스레드는 선점형이다)
 *
 * 실제로 2가지 일을 동시에 하는 것(CPU multi-core)은 병렬성이라 한다.
 *
 * 스레드
 * - 프로세스보다 작은 개념이다.
 * - 한 스레드는 오직 한 프로세스에만 포함되어 있다.
 * - 컨텍스트 스위칭 발생시 힙 영역은 공유되지만 스택 영역이 교체된다.
 * - os가 스레드를 강제로 멈추고 다른 스레드를 실행한다.
 *
 * 코루틴
 * - 스레드보다 작은 개념이다.
 * - 한 코루틴의 코드는 여러 스레드에서 실행될 수 있다.
 * - (한 스레드에서 실행하는 경우) 컨텍스트 스위칭 발생 시 메모리(힙, 스택) 교체가 없다.
 * - 코루틴은 스스로가 다른 코루틴에게 양보한다.
 */
