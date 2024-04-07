package delegation

import kotlin.reflect.KProperty

class Example {
    var p: String by Delegate()

    override fun toString() = "Example Class"
}

class Delegate() {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${prop.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String) {
        println("$value has been assigned to ${prop.name} in $thisRef")
    }
}

// Standard Delegates
class LazySample {
    init { println("created!") }

    val lazyStr: String by lazy {
        println("computed!")
        "my lazy"
    }
}

// Storing Properties in a Map
class User(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
}

fun main() {
    val e = Example()
    println(e.p)

    e.p = "NEW"

    // Standard Delegates
    val sample = LazySample()
    println("lazyStr = ${sample.lazyStr}")
    println(" = ${sample.lazyStr}")

    // Storing Properties in a Map
    val user = User(mapOf("name" to "John Doe", "age" to 25))
    println("name = ${user.name}, age = ${user.age}")
}
