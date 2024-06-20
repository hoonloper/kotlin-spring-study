import kotlinx.coroutines.*
import kotlinx.coroutines.future.await
import java.util.concurrent.CompletableFuture

class SuspendFunction

/**
 * suspend - 정지 / 중지 / 유예
 * 코루틴이 중지되었다가 **재개될 수 있는 지점** - suspension point
 * 될 수도 있고, 안될 수도 있다.
 *
 * - 콜백 지옥을 해결하여 동기적으로 동작하는 것처럼 보이게 도와준다.
 * - 여러 비동기 라이브러리를 사용할 수 있도록 도와준다.
 *
 * coroutineScope
 * - 추가적인 코루틴을 만들고, 주어진 함수 블록이 바로 실행된다.
 * - 만들어진 코루틴이 모두 완료되면 다음 코드로 넘어간다.
 *
 * withContext
 * - coroutineScope과 기본적으로 유사하다.
 * - context에 변화를 주는 기능이 추가적으로 있다.
 *
 * withTimeout / withTimeoutOrNull
 * - coroutineScope과 기본적으로 유사하다.
 * - 주어진 시간 안에 새로 생긴 코루틴이 완료되어야 한다.
 * - 주어진 시간안에 완료되지 못하면 throw or null을 반환한다.
 */
fun main(): Unit =
    runBlocking {
        val result2: Int? =
            withTimeoutOrNull(1_000L) {
                delay(1_500L)

                10 + 5
            }

        printWithThread(result2!!)

        val result =
            withTimeout(1_000L) {
                delay(1_500L)

                10 + 5
            }

        printWithThread(result)

        printWithThread("START")
        printWithThread(calculateResult())
        printWithThread("END")
    }

suspend fun calculateResult(): Int =
    withContext(Dispatchers.Default) {
        val num1 =
            async {
                delay(1_000L)
                10
            }

        val num2 =
            async {
                delay(1_000L)
                20
            }

        num1.await() + num2.await()
    }
// suspend fun calculateResult(): Int =
//    coroutineScope {
//        val num1 =
//            async {
//                delay(1_000L)
//                10
//            }
//
//        val num2 =
//            async {
//                delay(1_000L)
//                20
//            }
//
//        num1.await() + num2.await()
//    }

fun suspend2(): Unit =
    runBlocking {
        // Deferred에 의존하고 있다.
        // val result1: Deferred<Int> =
        //     async {
        //         call1()
        //     }

        // val result2 =
        //     async {
        //         call2(result1.await())
        //     }

        // printWithThread(result2.await())

        val result1 = call1()

        val result2 = call2(result1)

        printWithThread(result2)
    }

/**
 * coroutineScope
 * - 추가적인 코루틴을 만들고, 주어진 함수 블록이 바로 실행된다.
 * - 만들어진 코루틴이 모두 완료되면 다음 코드로 넘어간다.
 */
suspend fun call1(): Int {
    return CoroutineScope(Dispatchers.Default).async {
        Thread.sleep(1_000L)

        100
    }.await()
}

suspend fun call2(num: Int): Int {
    return CompletableFuture.supplyAsync {
        Thread.sleep(1_000L)

        num * 2
    }.await()
}

interface AsyncCaller {
    suspend fun call()
}

class AsyncCallerImpl : AsyncCaller {
    override suspend fun call() {
        TODO("Not yet implemented")
    }
}

fun suspend1(): Unit =
    runBlocking {
        launch {
            delay(300L) // launch에서 쓰이는 마지막 block 함수가 suspend 함수이다, suspending lambda
        }
    }
