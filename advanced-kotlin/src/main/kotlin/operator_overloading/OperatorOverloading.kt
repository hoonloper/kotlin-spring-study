package operator_overloading

import java.time.LocalDate

class OperatorOverloading {
}

data class Point(
    val x: Int,
    val y: Int,
) {
    fun zeroPointSymmetry(): Point = Point(-x, -y)

    // 연산자 오버로딩
    operator fun unaryMinus() = Point(-x, -y)

    operator fun unaryPlus(): Int = x

    operator fun inc() = Point(x + 1, y + 1)

    // operator fun dec() = x // inc, dec는 자신의 타입만 반환 가능
}

fun main() {
    val point = Point(20, -10)
    println(point.zeroPointSymmetry())
    println(-point)

    var point2 = Point(20, -10)
    println(++point2)

    LocalDate.of(2023, 1, 1).plusDays(3)
    LocalDate.of(2023, 1, 1) + Days(3)

    // 복합 대입 연산자
    var a = 1
    a += 1
}

data class Days(val day: Long)

val Int.d: Days
    get() = Days(this.toLong())

operator fun LocalDate.plus(days: Days): LocalDate {
    return this.plusDays(days.day)
}
