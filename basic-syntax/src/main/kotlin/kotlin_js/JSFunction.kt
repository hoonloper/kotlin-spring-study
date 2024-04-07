package kotlin_js

fun main() {
    js("alert(\"alert from Kotlin!\")")

    val json = js("{}")
    json.name = "Jane"
    json.hobby = "movies"

    println(JSON.stringify(json))
}