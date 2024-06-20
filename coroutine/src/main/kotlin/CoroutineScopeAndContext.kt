import kotlinx.coroutines.*
import java.util.concurrent.Executors

class CoroutineScopeAndContext

/**
 * [CoroutineScope]
 * - launch와 async는 CoroutineScope의 확장 함수이다.
 * - CoroutineContext 하나만을 지니고 있다.
 *   - 코루틴과 관련된 여러가지 데이터를 갖고 있다.
 *   - Dispatcher - 코루틴이 어떤 스레드에 배정될지를 관리하는 역할
 *
 * CoroutineScope - 코루틴이 탄생할 수 있는 영역
 * CoroutineContext - 코루틴과 관련된 데이터를 보관
 *
 * 코루틴의 Structured Concurrency 기반이다.
 * 클래스 내부에서 독립적인 CoroutineScope을 관리
 * - 해당 클래스에서 사용하던 코루틴을 한 번에 종료할 수 있다.
 *
 * [CoroutineContext]
 * - Map + Set을 합쳐놓은 상태
 * - Key(Element) - value로 데이터 저장
 * - 같은 key의 데이터는 유일
 *
 * [CoroutineDispatcher]
 * - 코루틴을 스레드에 배정하는 역할
 * - [Dispatchers.Default]
 *   - 가장 기본적인 디스패처
 *   - CPU 자원을 많이 쓸 때 권장
 *   - 별다른 설정이 없으면 이 디스패처가 사용된다.
 * - [Dispatchers.IO]
 *   - IO 작업에 최적화 되어 있다.
 * - [Dispatchers.Main]
 *   - 보통 UI 컴포넌트를 조작하기 위한 디스패처
 *   - 특정 의존성을 갖고 있어야 정상적으로 활용할 수 있다.
 * - [ExecutorService를 디스패처로]
 *   - asCoroutineDispatcher() 확장함수 활용
 */
fun main() {
    CoroutineName("나만의 코루틴") + Dispatchers.Default

    val threadPool = Executors.newSingleThreadExecutor()

    CoroutineScope(threadPool.asCoroutineDispatcher()).launch {
        printWithThread("새로운 코루틴")
    }
}

fun coroutineScopeAndContext() {
    CoroutineScope(Dispatchers.Default).launch {
        delay(1_000L)
        printWithThread("Job 1")

        // context 접근 가능
        coroutineContext + CoroutineName("이름")
        coroutineContext.minusKey(CoroutineName) // 제거도 가능
    }

    // CoroutineScope은 Block이 없어서 직접 슬립을 해줘야 한다.
    Thread.sleep(1_500L)

    val job =
        CoroutineScope(Dispatchers.Default).launch {
            delay(1_000L)
            printWithThread("Job 1")
        }

    // job.join() // join은 suspend 함수이기 때문에 suspend 함수에서만 사용할 수 있다.
}

class AsyncLogic {
    private val scope = CoroutineScope(Dispatchers.Default)

    fun doSomething() {
        scope.launch {
            // 무언가 코루틴이 시작되어 작업!
        }
    }

    fun destroy() {
        scope.cancel()
    }
}
