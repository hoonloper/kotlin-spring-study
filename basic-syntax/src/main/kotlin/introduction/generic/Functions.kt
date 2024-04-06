package introduction.generic

fun <E> mutableStackOf(vararg elements: E) = MutableStack(*elements)

fun main() {
    val stack = mutableStackOf(0.63, 3.15, 2.6)
    println(stack)
}