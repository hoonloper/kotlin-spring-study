package lateinit_lazy

import kotlin.properties.Delegates.notNull
import kotlin.properties.Delegates.observable
import kotlin.properties.Delegates.vetoable

class DelegateFun {
    // lateinit과 비슷한 역할
    // 아직 초기화되지 않은 상태로 사용되면 IllegalStateException일 발생함
    // Primitive 타입에 사용 가능
    var age: Int by notNull()

    // observable()
    // onChange 함수는 setter가 호출될 때마다 실행된다
    var age2: Int by observable(20) { _, oldValue, newValue ->
        println("옛날 값 : $oldValue / 새로운 값 : $newValue")
    }

    // true를 반환하면 값이 변경되고, false를 반환하면 값이 변경되지 않는다
    var age3: Int by vetoable(20) { _, oldValue, newValue ->
        oldValue >= newValue
    }

    // 이름을 변경하고 싶을 때 다른 프로퍼티로 위임할 수 있다.
    @Deprecated("age를 사용하세요!", ReplaceWith("age4"))
    var num: Int = 0

    var age4: Int by this::num
}

class PersonMap(map: Map<String, Any>, map2: MutableMap<String, Any>) {
    // map에서 키가 프로퍼티 이름인 것을 찾는다. map["name"], map["age"]
    val name: String by map
    val age: Int by map

    // 값을 변경하고 싶은 MutableMap은 var를 통해 사용할 수 있다.
    var name2: String by map2
    var age2: String by map2
}

fun main() {
    val d = DelegateFun()

    d.age2 = 30
    d.age2 = 30
}
