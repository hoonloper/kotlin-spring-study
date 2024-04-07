package scope_functions

class Person(val name: String, age: Int, about: String)

fun main() {
    val jake = Person("jake", 30, "aAndroid developer")
        .also { writeCreationLog(it) }
}