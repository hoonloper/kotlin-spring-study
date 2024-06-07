package higher_order_function_and_literal

class HigherOrderFunction

fun main() {
    // 람다식
    compute(5, 3) { a, b -> a + b }

    // 익명 함수
    compute(5, 3, fun(a: Int, b: Int) = a + b)
    compute(5, 3, fun(a: Int, b: Int) : Int {
        return a + b
    })
    compute(5, 3, fun(a, b) = a + b)

    // 함수 리터럴이란
    // 코드에 고정되어 있는 함수값
    // 함숫값 / 함수 리터럴 - 일반 함수와 달리 변수로 간주하거나 파라미터에 넣을 수 있는 함수
    // 람다 (프로그래밍 용어) - 이름이 없는 함수
    // 람다식 (코틀린 용어) 함숫값 / 함수 리터럴을 표현하는 방법 1
    // 익명 함수 (코틀린 용어) 함숫값 / 함수 리터럴을 표현하는 방법 2

    // 람다식과 익명 함수 차이
    compute(5, 3) { a, b -> a + b } // 반환 타입 적을 수 없다
    compute(5, 3, fun(a, b) = a + b) // 반환 타입 적을 수 있다

    iterate(listOf(1, 2, 3, 4, 5), fun(num) {
        if (num == 3) {
            return
        }
        println(num)
    })
    iterate(listOf(1, 2, 3, 4, 5), { num ->
        if (num == 3) {
            // return // 비 지역적 반환이라 하며, 'return' is not allowed here 에러 발생
        }
        println(num)
    })
}

fun compute(num1: Int, num2: Int, op: (Int, Int) -> Int) : Int {
    return op(num1, num2)
}

fun compute2(
    num1: Int,
    num2: Int,
    op: (Int, Int) -> Int = { a, b -> a + b },
    op2: (Int, Int) -> Int = fun(a, b) = a + b
) : Int {
    return op(num1, num2)
}

fun iterate(numbers: List<Int>, exec: (Int) -> Unit) {
    for (number in numbers) {
        exec(number)
    }
}

fun calculate(num1: Int, num2: Int, oper: Char): Int {
    return when (oper) {
        '+' -> num1 + num2
        '-' -> num1 - num2
        '*' -> num1 * num2
        '/' -> {
            if (num2 == 0) {
                throw IllegalArgumentException("0으로 나눌 수 없다")
            } else {
                num1 / num2
            }
        }
        else -> throw IllegalArgumentException("들어올 수 없는 연산자다 $oper")
    }
}

enum class Operator(
    private val oper: Char,
    val calcFun: (Int, Int) -> Int,
) {
    PLUS('+', { a, b -> a + b }),
    MINUS('-', { a, b -> a - b }),
    MULTIPLY('*', { a, b -> a * b }),
    DIVIDE('/', { a, b ->
        if (b == 0) {
            throw IllegalArgumentException("0으로 나눌 수 없다")
        } else {
            a / b
        }
    })
}

fun calculate2(num1: Int, num2: Int, oper: Operator) = oper.calcFun(num1, num2)
