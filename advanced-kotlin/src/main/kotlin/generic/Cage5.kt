package generic

fun main() {
    // Cage5<Int>()
    // Cage5<String>() // 제네릭 제약이 걸리게 됨
    // Cage5<Carp>()

    val cage = Cage5(mutableListOf(Eagle(), Sparrow()))
    cage.printAfterSorting()

    // Non null 제약 - 제네릭 제약으로 T : Any로 설정하면 Null이 허용되지 않는다
    // Cage2<GoldFish?>() // Warning
    // Cage2<GoldFish>() // Pass
}

abstract class Bird(
    name: String,
    private val size: Int,
) : Animal(name), Comparable<Bird> {
    override fun compareTo(other: Bird): Int {
        return this.size.compareTo(other.size)
    }
}

class Sparrow : Bird("참새", 100)
class Eagle : Bird("독수리", 500)

// 제네릭 제약 T : Animal
// 제약 조건을 여러개 둘 수 있다 - where T : Animal, T : Comparable<T>
class Cage5<T>(
    private val animals: MutableList<T> = mutableListOf()
) where T : Animal, T : Comparable<T> {
    fun printAfterSorting() {
        this.animals.sorted()
            .map { it.name }
            .let { println(it) }
    }

    fun getFirst(): T {
        return animals.first()
    }

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun moveFrom(otherCage: Cage5<T>) {
        this.animals.addAll(otherCage.animals)
    }
    fun moveTo(otherCage: Cage5<T>) {
        otherCage.animals.addAll(this.animals)
    }
}

// 제네릭을 통한 인터섹션 확장 함수
fun <T> List<T>.hasIntersection(other: List<T>): Boolean {
    return (this.toSet() intersect other.toSet()).isNotEmpty()
}
