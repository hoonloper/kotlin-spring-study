package more

import kotlin.reflect.KClass

class Tip2

// 꼬리 재귀 함수 최적화
fun factorial(n: Int): Int {
    return if (n <= 1) {
        1
    } else {
        n * factorial(n - 1)
    }
}

// 꼬리 재귀 함수가 되려면 재귀함수 호출 이후 추가 연산이 없어야 한다
// 함수 호출은 스택이 쌓이게 되는데 loop 문으로 변경되니 메모리 사용량이 낮아진다
tailrec fun factorialV2(n: Int, curr: Int = 1) : Int {
    return if (n <= 1) {
        curr
    } else {
        factorialV2(n - 1, n * curr)
    }
}

// 인라인 클래스
// 클래스의 프로퍼티가 인라인에 작성된 코드에 들어가게 된다
// 프로퍼티를 하나만 갖고 있어야 한다
@JvmInline
value class Key(val key: String)

class User(
    val id: Id<User>,
    val name: String,
)

class Book(
    val id: Id<Book>,
    val author: String,
)

fun handle(userId: Long, bookId: Long) {

}

fun handleV2(userId: Id<User>, bookId: Id<Book>) {

}

@JvmInline
value class Id<T>(val id: Long)

// 값 객체
@JvmInline
value class Number(val num: Long) {
    init {
        require(num in 1..10)
    }
}

// multiple catch
fun logic(n: Int) {
    when {
        n > 0 -> throw AException()
        n == 0 -> throw BException()
    }
    throw CException()
}

class AException : RuntimeException()
class BException : RuntimeException()
class CException : RuntimeException()


// multiple exception 보일러 플레이트 ======================================
class ResultWrapper<T>(
    private val result: Result<T>,
    private val knownExceptions: MutableList<KClass<out Throwable>>,
) {
    fun toResult(): Result<T> {
        return this.result
    }

    fun onError(vararg exceptions: KClass<out Throwable>, action: (Throwable) -> Unit): ResultWrapper<T> {
        this.result.exceptionOrNull()?.let {
            if (it::class in exceptions && it::class !in this.knownExceptions) {
                action(it)
            }
        }
        return this
    }
}

fun <T> Result<T>.onError(vararg exceptions: KClass<out Throwable>, action: (Throwable) -> Unit): ResultWrapper<T> {
    exceptionOrNull()?.let {
        if (it::class in exceptions) {
            action(it)
        }
    }
    return ResultWrapper(this, exceptions.toMutableList())
}
// ============================================================================

fun main() {
    val key = Key("비밀 번호")

    println(key)

    val userId = 1L
    val bookId = 2L
    handle(bookId = bookId, userId = userId)

    val inlineUserId = Id<User>(1L)
    val inlineBookId = Id<Book>(2L)
    handleV2(bookId = inlineBookId, userId = inlineUserId)

    try {
        logic(10)
    } catch(e: Exception) {
        when(e) {
            is AException,
            is BException -> TODO()
            is CException -> TODO()
        }
        throw e
    }

    runCatching { logic(10) }
        .onError(AException::class, BException::class) {
            println("A 또는 B 예외가 발생했습니다.")
        }
        .onError(CException::class) {

        }
}
