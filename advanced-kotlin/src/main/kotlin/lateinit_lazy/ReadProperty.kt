package lateinit_lazy

import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ReadProperty

// ReadOnlyProperty는 getValue, setValue를 기억하지 않아도 된다
class LazyInitReadOnlyProperty<T>(val init: () -> T) : ReadOnlyProperty<Any, T> {
    private var _value: T? = null
    val value: T
        get() {
            if (_value == null) {
                this._value = init()
            }
            return _value!!
        }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value
    }
}

class Person4 {
    val name: String by LazyInitReadOnlyProperty {
        Thread.sleep(2_000)
        "김수한무"
    }

    // 임시적으로 사용할 수 있다
    val status: String by object : ReadOnlyProperty<Person4, String> {
        private var isGreen: Boolean = false

        override fun getValue(thisRef: Person4, property: KProperty<*>): String {
            return if (isGreen) {
                "Happy"
            } else {
                "Sad"
            }
        }
    }
}

// 특정 프로퍼티만 위임 허용하기
class Person5 {
    val name by DelegateProvider("이름") // 정상 동작
    val country by DelegateProvider("한국") // 정상 동작하면 안됨
}

class DelegateProvider(
    private val initValue: String
) : PropertyDelegateProvider<Any, DelegateProperty> {
    override fun provideDelegate(thisRef: Any, property: KProperty<*>): DelegateProperty {
        if (property.name != "name") {
            throw IllegalArgumentException("name만 연결 가능합니다.")
        }
        return DelegateProperty(initValue)
    }
}

class DelegateProperty(
    private val initValue: String,
) : ReadOnlyProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return initValue
    }
}

fun main() {
    Person5()
}

interface Fruit {
    val name: String
    val color: String

    fun bite()
}

open class Apple: Fruit {
    override val name: String
        get() = "사과"
    override val color: String
        get() = "빨강"

    override fun bite() {
        println("아삭")
    }
}

// 첫번째 방법
class GreenApple : Fruit {
    override val name: String
        get() = "사과"
    override val color: String
        get() = "초록"

    override fun bite() {
        println("아삭")
    }
}

// 두번째 방법
class GreenApple2 : Apple() {
    override val color: String
        get() = "초록"
}

// 세번째 방법 - 상속보다는 합성
class GreenApple3(
    private val apple: Apple
) : Fruit {
    override val name: String
        get() = apple.name
    override val color: String
        get() = "초록색"

    override fun bite() = apple.bite()
}

// 네번째 방법 - 클래스 위임
class GreenApple4(
    private val apple: Apple
) : Fruit by apple {
    override val color: String
        get() = "초록색"
}
