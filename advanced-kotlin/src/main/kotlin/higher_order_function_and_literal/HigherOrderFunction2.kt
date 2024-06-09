package higher_order_function_and_literal

class HigherOrderFunction2

fun main() {
    val add = { a: Int, b: Int -> a + b }
    add.invoke(1, 2)

    val add2 = fun Int.(other: Long): Int = this + other.toInt()
    5.add2(3L)

    // 고차 함수에서 함수를 넘기면 FunctionN 객체가 만들어진다
    compute2(5, 3) { num1, num2 -> num1 * num2 }

    var num = 5
    num += 1
    val plusOne: () -> Unit = { num += 1 }

    // 고차 함수를 사용하게 되면 FunctionN 클래슥 ㅏ만들어지고 인스턴스화되어야 하므로 오버헤드가 발생할 수 있다
    // 함수에서 변수를 포획할 경우, 해당 변수를 Ref라는 객체로 감싸야 한다. 때문에 오버헤드가 발생할 수 있다
    // 고차 함수는 일반 함수보다 오버헤드가 발생하지만 유연한 프로그래밍 즉, 간단하게 사용할 수 있다
}

fun compute2(num1: Int, num2: Int, op: (Int, Int) -> Int): Int {
    return op(num1, num2)
}

fun onGenerator2(): (Int, Int) -> Int {
    return { a, b -> a + b }
}

// 앞 Int 확장 함수의 타입, this 수신 객체
fun Int.add(other: Long): Int = this + other.toInt()
