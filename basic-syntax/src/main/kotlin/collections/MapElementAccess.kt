package collections

import java.util.NoSuchElementException

val map = mapOf("key" to 42)

val value1 = map["key"]
val value2 = map["key2"]

val value3: Int = map.getValue("key")

val mapWithDefault = map.withDefault { k -> k.length }
val value4 = mapWithDefault.getValue("key2")

fun main() {
    try {
        map.getValue("anotherKey")
    } catch (e: NoSuchElementException) {
        println("Message: $e")
    }
}
