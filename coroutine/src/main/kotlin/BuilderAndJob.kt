import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class BuilderAndJob

/**
 * runBlocking은 코루틴 빌더중 하나!
 * - 프로그램에 진입할 때 최초로 적용해주거나 (main)
 * - 특정 상황(테스트)에서 사용해주는 것이 좋다.
 */
fun example1() {
    runBlocking {
        printWithThread("START")
        launch {
            delay(2_000L) // yield()
            printWithThread("LAUNCH END")
        }
    }
    // launch 종료할 때까지 실행 안됨
    // runBlocking에 의해 코루틴이 스레드에 잡혀있음
    printWithThread("END")
}

fun example2(): Unit =
    runBlocking {
        // CoroutineStart.LAZY를 통해 job으로 코루틴을 제어할 수 있다.
        // 시작 외에도 취소, 대기 등도 제어할 수 있다.
        val job =
            launch(start = CoroutineStart.LAZY) {
                printWithThread("Hello Launch")
            }
        delay(1_000L)
        job.start()
    }

fun example3(): Unit =
    runBlocking {
        // 취소 제어
        val job =
            launch {
                (1..5).forEach {
                    printWithThread(it)
                    delay(500)
                }
            }

        delay(1_000L)

        job.cancel() // 2번정도 출력되고 코루틴이 취소된다.
    }

/**
 * [Job 객체]
 * start() : 시작 신호
 * cancel() : 취소 신호
 * join() : 코루틴이 완료될 때까지 대기
 */
fun example4(): Unit =
    runBlocking {
        // job1이 대기에 들어간 순간 다음 코루틴(job2)가 접근하기 때문에 2초를 기다리는 것이 아닌 1초보다 조금 더 걸리면서 끝난다.
        val job1 =
            launch {
                delay(1_000L)
                printWithThread("Job 1")
            }

        job1.join() // join을 사용하면 job1이 끝날 때까지 다른 코루틴이 대기한다.

        val job2 =
            launch {
                delay(1_000L)
                printWithThread("Job 2")
            }
    }

/**
 * async의 경우 주어진 함수의 실행 결과를 반환할 수 있다!
 * async는 callback을 이용하지 않고 동기 방식으로 코드를 작성할 수 있다.
 *
 */
fun main(): Unit =
    runBlocking {
        // 정확하게는 Deferred 객체를 반환한다.
        val job =
            async {
                3 + 5
            }

        // async에는 await 기능을 제공한다.
        job.await() // await은 async의 결과를 가져오는 함수이다.

        val time =
            measureTimeMillis {
                val job1 = async { apiCall1() }
                val job2 = async { apiCall2() }

                printWithThread(job1.await() + job2.await()) // 3이 출력된다
            }

        printWithThread("소요 시간: $time ms")

        // 콜백 지옥에 걸릴 수 있다.
        // apiCall1(object : Callback {
        //     apiCall2(object : Callback) {
        //         apiCall3() // ...
        //     }
        // })

        // async는 콜백 지옥을 벗어날 수 있다.
        val time2 =
            measureTimeMillis {
                val job1 = async { apiCall1() }
                val job3 = async { apiCall3(job1.await()) }

                printWithThread(job3.await()) // 4가 출력된다
            }

        printWithThread("소요 시간: $time2 ms")

        // * 다만, CoroutineStart.LAZY 옵션을 사용하면, await() 함수를 호출했을 때 계산 결과를 계속 기다린다.
        // 즉, await()을 호출할 때 내부 기능이 동작하기 때문에 호출시점에 delay가 걸려 동기적으로 동작하는 것처럼 보인다.
        // 이 상황을 해결하는 방법으로 미리 start()를 호출하면 된다.
        val time3 =
            measureTimeMillis {
                val job1 = async(start = CoroutineStart.LAZY) { apiCall1() }
                val job2 = async(start = CoroutineStart.LAZY) { apiCall2() }

                job1.start()
                job2.start()
                printWithThread(job1.await() + job2.await()) // 3이 출력된다
            }

        printWithThread("소요 시간: $time3 ms")
    }

suspend fun apiCall1(): Int {
    delay(1_000L)
    return 1
}

suspend fun apiCall2(): Int {
    delay(1_000L)
    return 2
}

suspend fun apiCall3(num: Int): Int {
    delay(1_000L)
    return num + 3
}
