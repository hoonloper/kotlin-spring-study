package introduction.functions

fun main() {
    fun printAll(vararg messages: String) {
        for (m in messages) println(m)
    }
    printAll("Hello", "Hello2", "Hello3", "Hello4")

    fun printAllWithPrefix(vararg messages: String, prefix: String) {
        for (m in messages) println(prefix + m)
    }
    printAllWithPrefix(
        "Hello", "Hello2", "Hello3", "Hello4",
        prefix = "Greeting: "
    )

    fun log(vararg entries: String) {
        printAll(*entries)
    }
    log("Hello", "Hello2", "Hello3", "Hello4")
}