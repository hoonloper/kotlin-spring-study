package annotation_and_reflection

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.cast
import kotlin.reflect.full.createType
import kotlin.reflect.full.hasAnnotation

fun main() {
    executeAll(Reflection()) // a입니다

    // KClass<T> - kotlin Class
    // Class<T> - java Class
    val kClass: KClass<Reflection> = Reflection::class

    val ref = Reflection()
    val kClass2: KClass<out Reflection> = ref::class

    val kClass3: KClass<out Any> = Class.forName("annotation_and_relection.Reflection").kotlin
    kClass.java // Class<Reflection>
    kClass.java.kotlin // KClass<Reflection>
    // JVM 위에서 동작하기에 kotlin, java 리플렉션 모두 사용 가능하다
    // 적절하게 필요한 것을 사용하면 된다

    // KType, 타입을 표현
    val kType: KType = GoldFish::class.createType()

    // KParameter, 매개변수
    // KTypeParameter
    // KCallable, 무언가 호출될 수 있느냐는 의미
    // KCallable 구현체 KFunction, KProperty
    val goldFish = GoldFish("금붕")
    // 인스턴스를 만들어도 GoldFish에 대한 class를 가져온 것이라 인스턴스된 객체를 넘겨줘야 한다
    goldFish::class.members.first { it.name == "print" }.call(goldFish)

    // KClassifier - 클래스인지 타입 파라미터인지 구분
    // KAnnotatedElemen - 어노테이션이 붙을 수 있는 언어 요소
    // KClass - 코틀린 클래스를 표현
    // KType - 코틀린에 있는 "타입"을 표현
    // KParameter - 코틀린에 있는 파라미터를 표현
    // KTypeParameter - 코틀린에 있는 타입파라미터를 표현
    // KCallable - 호출될 수 있는 언어 요소를 표현
    // KFunction - 코틀린에 있는 함수를 표현
    // KProperty - 코틀리넹 있는 프로퍼티를 표현
}

class GoldFish(val name: String) {
    fun print() {
        println("이름은 $name")
    }
}

// 캐스트 함수
fun castToGoldFish(obj: Any): GoldFish {
    return GoldFish::class.cast(obj)
}

@Target(AnnotationTarget.CLASS)
annotation class Executable

@Executable
class Reflection {
    fun a() {
        println("a입니다")
    }

    fun b(n: Int) {
        println("b입니다")
    }
}

fun executeAll(obj: Any) {
    val kClass = obj::class
    if (!kClass.hasAnnotation<Executable>()) {
        return
    }

    val callableFunctions = kClass.members.filterIsInstance<KFunction<*>>()
        .filter { it.returnType == Unit::class.createType() }
        .filter { it.parameters.size == 1 && it.parameters[0].type == kClass.createType() }

    callableFunctions.forEach { function ->
        // function.call(kClass.createInstance())
        function.call(obj) // 생성자가 없으니 객체를 직접 넣어주는 것도 좋다
    }
}
