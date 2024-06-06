package lateinit_lazy

import kotlin.reflect.KProperty

class Person3 {
    // 위임 패턴
    private val delegateProperty =
        LazyInitProperty {
            Thread.sleep(2_000)
            "김수한무"
        }

    val name: String
        get() = delegateProperty.value

    // 위 위임 패턴과 동일
    // lazy는 Thread safe하다
    val lazyName: String by lazy {
        Thread.sleep(2_000)
        "김수한무"
    }
}

class LazyInitProperty<T>(val init: () -> T) {
    private var _value: T? = null
    val value: T
        get() {
            if (_value == null) {
                this._value = init()
            }
            return _value!!
        }

    operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value
    }
}
