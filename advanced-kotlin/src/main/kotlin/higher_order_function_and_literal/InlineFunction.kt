package higher_order_function_and_literal

class InlineFunction

fun main() {
    // exec에 넣은 println까지 inline이 되어버린다
    // 즉 자신 외에도 다른 함수까지 inlining 시킨다
    // 단 메서드 인자 혹은 알 수 없는 경로에서 들어온 exec은 inlining되지 않는다
    // 혹은 noinline을 붙이게 되면 인라인되지 않는다
    repeat(2) { println("HelloWorld") }


    // 비 지역적 return이 사용 가능해진다!!
    // 단 return은 main 함수를 return하게 된다! 람다가 return되는 게 아님
    // 근데 3을 제외하고 출력해야 하는데 우리의 의도와 맞지 않다
    // non-local을 금지시킬 수 있을까? 바로 crossinline을 붙이면 된다
    iterate2(listOf(1, 2, 3, 4, 5)) { num ->
        if (num == 3) {
//            return
        }
        println(num)
    }
}

class Person(val name: String) {
    // 이것 또한 본문으로 들어간다
    inline val uppercaseName: String
        get() = this.name.uppercase()
}

inline fun iterate2(numbers: List<Int>, crossinline exec: (Int) -> Unit) {
    for (num in numbers) {
        exec(num)
    }
}

// 인라인을 사용하면 함수 본문이 함수 호출부에 들어가게 된다!!
inline fun add(num1: Int, num2: Int): Int {
    return num1 + num2
}

inline fun repeat(times: Int, exec: () -> Unit) {
    for (i in 1..times) {
        exec()
    }
}
