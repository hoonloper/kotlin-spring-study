package scope_functions

fun main() {
    with(configuration) {
        println("$host:$port")
    }
    println("${configuration.host}:${configuration.port}")
}