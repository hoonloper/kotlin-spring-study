package introduction.generic

class MutableStack<E>(vararg items: E) {
    private val elements = items.toMutableList()

    fun push(element: E) = elements.add(element)

    fun peek(): E = elements.last()

    fun pop(): E = elements.removeAt(elements.size - 1)

    fun isEmpty() = elements.isEmpty()

    fun size() = elements.size

    override fun toString() = "MutableStack(${elements.joinToString()})"
}

fun main() {
    val stack = MutableStack("test", "test2")

    stack.push("test3")

    println(stack.peek())

    println(stack.pop())

    println(stack.isEmpty())

    println(stack.size())

    println(stack.toString())
}