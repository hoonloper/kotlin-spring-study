const val CONSTANT = 10 // Top Level 상수

fun main(args: Array<String>) {
    println("Hello World!")

    var i = 10 // 변수
    val j = 10 // 상수

    var int: Int = 1 // 타입
    var str: String = "string" // 타입

    // 형변환
    var x = 10
    var l = 20L // Long
    // l = i 타입이 달라 할당 안됨
    l = i.toLong() // 이건 됨
    i = l.toInt()
    str = i.toString()
    i = str.toInt()

    // 문자열
    var name = "hello"
    print("이름" + name + "이다")
    print("이름 $name 이다")
    print("이름 ${name + "수식이 필요하면 이렇게"} 이다")
}