import kotlinx.coroutines.*

class Cancel

/**
 * 필요하지 않은 코루틴을 적절히 취소해 컴퓨터 자원을 아껴야 한다!
 * 코루틴도 취소에 협조해줘야 한다.
 */
fun cancel1(): Unit =
    runBlocking {
        val job1 =
            launch {
                delay(1_000L)
                printWithThread("Job 1")
            }

        val job2 =
            launch {
                delay(1_000L)
                printWithThread("Job 2")
            }

        /**
         * [취소에 협조하는 방법]
         * - delay() / yield() 같은 kotlinx.coroutines 패키지의 suspend 함수 사용!
         */
        delay(100)
        job1.cancel()
    }

fun cancel2(): Unit =
    runBlocking {
        /**
         * [취소에 협조하는 방법]
         * - 코루틴 스스로 본인의 상태를 확인해 취소 요청을 받았으면, CancellationException을 던지기!
         */
        val job =
            launch(Dispatchers.Default) { // Dispatchers.Default는 launch에 의해 생성한 코루틴은 메인 스레드가 아닌 다른 스레드로 배정하여 동작한다.
                var i = 1
                var nextPrintTime = System.currentTimeMillis()

                while (i <= 5) {
                    if (nextPrintTime <= System.currentTimeMillis()) {
                        printWithThread("${i++} 번째 출력!")
                        nextPrintTime += 1_000L
                    }

                    // cancel 명령을 받아야만 isActive가 false된다
                    if (!isActive) {
                        throw CancellationException()
                    }
                }
            }

        delay(100)
        job.cancel() // 취소가 되지 않는다!
    }

/**
 * [취소에 협조하는 방법 요약 정리]
 * - kotlinx.coroutines 패키지의 suspend 함수를 호출
 * - isActive로 CancellationException을 던지기
 */

fun main(): Unit =
    runBlocking {
        val job =
            launch {
                try {
                    delay(100L)
                } catch (e: CancellationException) {
                    // 아무것도 안함
                } finally {
                    // 필요한 자원을 받을 수도 있음
                }

                printWithThread("delay에 의해 취소되지 않았다.")
            }

        delay(100L)
        printWithThread("취소 시작")
        job.cancel()
    }
