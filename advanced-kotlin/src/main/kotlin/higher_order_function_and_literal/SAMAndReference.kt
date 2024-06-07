package higher_order_function_and_literal

class SAMAndReference

// SAM
// 추상 메서드가 하나만 존재
// 자바에서는 이런 SAM을 람다로 만들 수 있다
// public interface Runnable {
//     public abstract void run();
// }

// 코틀린은 람다 함수로 만들 수 없다!
fun main() {
    // 람다 앞에 인터페이스를 정의하면 람다로 사용할 수 있다
    // 이런 형태를 SAM 생성자라 한다
    // val filter = StringFilter { s -> s.startsWith("A") }

    // 단, 파라미터에 바로 넣는 경우 람다 형태로 사용할 수 있다
    // consumeFilter({ s-> s.startsWith("A") })

    // 그런데 람다 파라미터 인자는 제네릭 형태와 겹칠 수 있다
    // 이럴 때는 제네릭 처럼 추상된 것보다 실질적인 타입이 명시된 구체적인 것이 결정된다
    KStringFilter { "A".startsWith("A") }
    // KStringFilter2 { "A".startsWith("A") } Error
}

// 코틀린에서 SAM interface를 만들고 싶어면 fun을 붙이면 된다
fun interface KStringFilter {
    fun predicate(str: String): Boolean
}

interface KStringFilter2 {
    fun predicate(str: String): Boolean
}
