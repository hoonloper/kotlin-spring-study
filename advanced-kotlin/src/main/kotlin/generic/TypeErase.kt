package generic

fun main() {
    // 런타임 때는 타입 정보가 사라지는데
    // 이를 타입 소거 라고 말한다.

    val num = 3
    num.toSuperString() // "Int: 3"

    val str = "ABC"
    str.toSuperString() // "String: ABC"

    val numbers = listOf(1, 2f, 3.0)
    numbers.filterIsInstance<Float>() // [2f] -> inline reified 조합 사용
}

inline fun <reified T> T.toSuperString() {
    // 클래스 정보를 가져오려할 때 에러가 발생한다
    // 제네릭 타입은 런타임에서 타입이 사라지기 때문에 class 정보를 가져오지 못하는 것이다
    println("${T::class.java.name}: $this")
}

// inline은 코드의 본문을 호출 지점으로 이동시켜 컴파일되는 함수
// reified 키워드 + inline 함수
// reified 키워드의 한계
// - T의 인스턴스를 만들거나 / T의 companion object를 가져올 수는 없다.
inline fun <reified T> List<*>.hasAnyInstanceOf(): Boolean {
    return this.any { it is T }
}

class TypeErase

fun checkStringList(data: Any) {
    // Error: Cannot check for instance of erased type: List<String>
    // 런타임에서는 타입이 소거되어 무슨 타입인지 모른다!
    // if (data is List<String>) { }
    // star projection을 사용하면 된다!
    // 타입 정보는 모르지만 List만은 확실하면 사용하고, List 기능을 사용할 수 있다.
    if (data is List<*>) {
        val element: Any? = data[0] // Any?로 가져오게 된다
    }
    if (data is MutableList<*>) {
        // data.add("2") // 안된다 List인지, MutableList인지 모른다
    }

    // 그러나! 함수 인자가 Collection 처럼 명확하다면?
    fun test(data2: Collection<String>) {
        if (data2 is MutableList<String>) {
            data2.add("문자열") // 이것은 가능하다!
        }
    }
}

// 타입 파라미터 쉐도잉
class CageShadow<T : Animal> {
    // 클래스의 T가 함수의 T에 의해 쉐도잉 되는 것
    fun <T : Animal> addAnimal(animal: T) {
        // 전역 변수와 지역 변수의 개념과 비슷함
        // 함수의 T : Animal이 class의 T : Animal을 덮어씌운다
    }
}

open class CageV1<T : Animal> {
    fun addAnimal(animal: T) {
    }
}

class CageV2<T : Animal> : CageV1<T>()

class GoldFishCageV2 : CageV1<GoldFish>()

fun handleCacheStoreV1(store: Map<String, MutableList<String>>) {}

typealias Store = Map<String, MutableList<String>>
fun handleCacheStoreV2(store: Store) {}
