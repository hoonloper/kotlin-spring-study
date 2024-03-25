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
    values3.set(2, 2)
    values3[0] = 10
    values3[1] = 20

    // 예외 처리
    try {
        val value = values3[4]
    } catch (e: Exception) {
        print(e.message)
    }

    // Null Safety
    var value4: String? = null
    value4 = ""
    value4 = null

    var value5: String = ""
    if (value4 != null) {
        value5 = value4
    }
    value4?.let {
        value5 = value4
    }


    // 함수
    fun sum(a: Int, b: Int) : Int {
        return a + b
    }
    fun sum2(a: Int, b: Int) = a + b // 한줄이면 생략 가능
    // fun sum2(a: Int, b: Int, c: Int) = a + b + c // 오버로딩?
    fun sum2(a: Int, b: Int, c: Int = 0) = a + b + c // 기본값 지정 가능
    print(sum(1, 2))
    print(sum(a = 10, b = 20))

    // 클래스
    val john = Person("John", 20)
    print(john.name + john.age)

    val john2 = Person("John", 20)
    print(john2.name + john2.age)
    println(john)
    println(john2)
    println(john == john2)

    // 타입 체크 is
    val dog: Animal = Dog()
    val cat: Animal = Cat()

    if (dog is Dog) {
        // Dog 타입을 통과했기에 draw, move 메서드 둘 다 사용 가능
        print("멍멍이")
    }
    if (cat is Cat) {
        print("고양이")
    }
}

//class Person (
//    val name: String,
//    val age: Int,
//) {}
data class Person (
    val name: String,
    val age: Int,
)

class Person2(
    val name: String,
    val age: Int
) {
    var hobby = "축구"
        private set // 외부에서 set이 불가능해진다
        get() = "취미 : $field" // 게터

    init {
        print("init")
    }

    fun some() {
        hobby = "농구"
    }
}

// 상속
abstract class Animal {
    open fun move() { // open으로 override 풀어주기
        print("이동")
    }
}

interface Drawable {
    fun draw()
}

class Dog: Animal(), Drawable {
    override fun move() {
        print("껑충")
    }

    override fun draw() {
        TODO("Not yet implemented")
    }
}

class Cat: Animal(), Drawable {
    override fun move() {
        print("살금")
    }

    override fun draw() {
        TODO("Not yet implemented")
    }
}

//open class Person() // 일반 클래스 상속 안됨(open을 붙여줘야함)
//
//class SuperMan : Person()


// generic
class Box<T>(val value: T)
val box = Box<Int>(10)
val box2 = Box<String>("str")
