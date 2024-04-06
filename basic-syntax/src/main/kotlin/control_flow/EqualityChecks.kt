package control_flow

fun main() {
    val authors = setOf("Shakespeare", "Hemingway", "Twain")
    val writers = setOf("Twain", "Shakespeare", "Hemingway")

    /**
     * a == b compiles down to if (a == null) b == null else a.equals(b)
     */
    println(authors == writers)
    println(authors === writers)
}