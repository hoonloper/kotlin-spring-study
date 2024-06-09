package more

class Tip3

// [kdoc] <Block Tag> - @tag
/**
 * 박스 클래스. 생성자에 name이 들어간다. - 요약하는 줄
 *
 * **강조**
 * - A
 * - B
 * - C
 *
 * @param T 박스의 아이템 타입
 * @property name 박스의 이름
 * @sample more.helloWorld
 */
class Box<T>(val name: String) {
    fun add(item: T): Boolean {
        TODO()
    }
}

fun helloWorld() {
    println("Hello World")
}
