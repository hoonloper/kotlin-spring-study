package higher_order_function_and_literal

class Reference

fun add2(a: Int, b: Int) = a + b

fun main() {
    val add1 = { a: Int, b: Int -> a + b }
    val add2 = fun (a: Int, b: Int) = a + b
    val add3 = ::add2 // 호출 가능 참조 (callable reference)

    val personConstructor = Person2::name.getter // 프로퍼티에 대한 호출 가능 참조를 얻을 수도 있다
    val p1 = Person2("사람", 20)
    val boundingGetter = p1::age.getter

    // Java에서는 호출 가능 참조 결과값이 Consumer / Supplier 같은 함수형 인터페이스이지만
    // Kotlin에서는 리플렉션 객체이다!!!
}

class Person2(
    val name: String,
    val age: Int
)
