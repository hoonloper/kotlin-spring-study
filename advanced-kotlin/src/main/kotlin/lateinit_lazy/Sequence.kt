package lateinit_lazy

fun main() {
    val fruits = listOf(
        MyFruit("사과", 1_000),
        MyFruit("바나나", 3_000),
    )

    // sequence는 한 원소에 대한 모든 계산을 처리 후 넘어간다
    // 즉 iterable을 통해 체이닝을 하면 각 계산별로 새로운 원소가 만들어져서 메모리를 차지하는 것을 방지할 수
    // 또한 최종 결과가 나오기 전까지 계산이 되지 않는다
    val avg = fruits.asSequence()
        .filter { it.name == "사과" }
        .map { it.price }
        .take(10_000)
        .average()
}

data class MyFruit(
    val name: String,
    val price: Long
)
