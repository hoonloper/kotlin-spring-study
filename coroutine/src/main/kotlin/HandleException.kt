import kotlinx.coroutines.*

class HandleException

fun main(): Unit =
    runBlocking {
        // CoroutinScope은 새로운 영역에서 코루틴을 만든다.
        // 즉, 메인 스레드가 아닌 다른 스레드에서 코루틴을 만든다.
        // val job1 =
        //     CoroutineScope(Dispatchers.Default).launch {
        //         delay(1_000L)
        //         printWithThread("Job 1")
        //    }

        // val job2 =
        //     CoroutineScope(Dispatchers.Default).launch {
        //         delay(1_000L)
        //         printWithThread("Job 2")
        //    }

        // launch는 예외가 바로 발생한다.
        // val job =
        //     CoroutineScope(Dispatchers.Default).launch {
        //         throw IllegalArgumentException()
        //    }

        // async는 예외를 얻으려면 await()을 호출해야 한다.
        val job =
            CoroutineScope(Dispatchers.Default).async {
                throw IllegalArgumentException()
            }

        delay(100)
        job.await() // 메인 스레드에서 호출하기 때문에 메인 스레드에서 예외가 발생한다. 코루틴 예외도 함께 보여준다.

        // 이럴 때는 예외가 바로 발생한다.
        // 즉, 자식 코루틴에서 발생한 예외는 부모 코루틴에 전파가 된다.
        // 새로운 루트 코루틴을 만들면 전파할 곳이 없어서 예외가 발생하지 않지만 자식 코루틴은 전파를 시킨다!
        val job2 =
            async {
                throw IllegalArgumentException()
            }

        delay(1_000L)

        // 만약 자식 코루틴에서 부모로 전파하고 싶지 않다면??
        // SupervisorJob()을 사용한다
        val job3 =
            async(SupervisorJob()) {
                throw IllegalArgumentException()
            }

        delay(1_000L)

        val job4 =
            launch {
                try {
                    throw IllegalArgumentException()
                } catch (e: IllegalArgumentException) {
                    printWithThread("정상 종료")
                }
            }

        val exceptionHandler =
            CoroutineExceptionHandler { context, throwable ->
                printWithThread("예외")
            }

        val job5 =
            CoroutineScope(Dispatchers.Default).launch(exceptionHandler) {
                throw IllegalArgumentException()
            }

        delay(1_000L)
    }

/**
 * [예외를 다루는 방법]
 * - 직관적인 try - catch - finally
 * - CoroutineExceptionHandler - 예외 발생 이후 로깅 / 에러 메시지 전송 등에 활용
 *   - launch에만 적용 가능
 *   - 부모 코루틴이 있으면 동작하지 않는다.

 * [코루틴 취소 예외 한 방 정리]
 * - 발생한 예외가 CancellationException인 경우 취소로 간주하고 부모 코루틴에게 전파 X
 * - 그 외 다른 예외가 발생한 경우 실패로 간주하고 부모 코루틴에게 전파 O
 * - 다만 내부적으로는 취소나 실패 모두 "취소됨" 상태로 관리한다.
 *
 * [Job(코루틴)의 Life Cycle]
 * NEW -> ACTIVE(실패) -> CANCELLING -> CANCELLED
 *        ACTIVE(성공) -> COMPLETING -> COMPLETED
 * NEW - LAZY로 만들면 start했을 때 ACTIVE되는데 일반 NEW는 바로 ACTIVE된다
 *
 * [Structured Concurrency]
 * 왜 완료가 두 단계로 나뉘어져 있을까?
 * - 자식 코루틴을 기다려야 하기 때문!
 * - 즉, 자식 코루틴이 여러개 존재할 경우 어디서 예외가 추가로 발생할지 모르기 때문이다.
 * - 자식 코루틴에서 예외가 부모로 전파되면, 부모에서 자식으로 취소 요청을 보내게 된다.
 *
 * 부모 - 자식 관계 코루틴이 한 몸처럼 움직이는 것을 Structured Concurrency 라고 한다.
 * 수많은 코루틴이 유실되거나 누수되지 않도록 보장한다.
 * 코드 내의 에러가 유실되지 않고 적절히 보고될 수 있도록 보장한다.
 *
 * [취소, 예외, Structured Concurrency 총 정리]
 * - 자식 코루틴에서 예외가 발생하는 경우 Structured Concurrency에 의해 부모 코루틴이 취소되고 부모 코루틴의 다른 자식 코루틴들도 취소된다.
 * - 자식 코루틴에서 예외가 발생하지 않더라도 부모 코루틴이 취소되면 자식 코루틴들이 모두 취소된다.
 * 다만, CancellationException은 정상적인 취소로 간주하기 때문에 부모 코루틴에게 전파되지 않고, 부모 코루틴의 다른 자식 코루틴을 취소 시키지도 않는다.
 */
