import java.util.Scanner
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

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

    // max, min
    var n = 10
    var m = 20
    print(max(n, m))
    print(min(n, m))

    // random
    // val randomNum = Random.nextInt()
    val randomNum = Random.nextInt(0, 100) // 0 ~ 99
    println(randomNum)

    // 키보드 입력
    val reader = Scanner(System.`in`) // in은 코틀린에서 사용할 수 없고 이런것들은 ``이 붙음
    reader.next()

    // if문
    val q = 5
    if (q > 5) {
        print(q)
    } else if (q > 3) {
        print(q)
    } else {
        print(0)
    }
    when {
        q > 5 -> { print(q) }
        q > 3 -> { print(q) }
        else -> { print(0) }
    }
    val returned = if (q > 5) {
        q
    } else if (q > 3) {
        q
    } else {
        0
    }

    // 반복문
    val items = listOf(1, 2, 3, 4, 5)
    for (item in items) {
        print(item)
    }
    items.forEach { item ->
        print(item)
    }
    for (i in 0..items.size - 1) {
        print(i)
        break
        continue
    }

    // 리스트
    val values = listOf(1, 2, 3, 4, 5) // 불변 리스트다
    val values2 = mutableListOf(1, 2, 3, 4, 5) // 가변 리스트다
    values2.add(1)
    values2.remove(1)

    // Array, 거의 사용 안함
    val values3 = arrayOf(1, 2, 3)
    values3.size
    values3.get(0)
    values3.set(2)
    values3[0] = 10
    values3[1] = 20

    // 예외 처리
    try {
        val value = values3[4]
    } catch (e: Exception) {
        print(e.message)
    }
}