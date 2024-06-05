package generic

fun main() {
    val cage = Cage()
    cage.put(Carp("잉어"))

    // 가장 간단한 방법 - 타입 캐스팅
    // 런타임에 에러가 발생하기에 위험하다
    // val carp: Carp = cage.getFirst() as Carp

    // 다음 간단한 방법 - 타입 캐스팅
    // 이것 또한 에러가 발생한다
    // val carp: Carp = cage.getFirst() as? Carp
    //     ?: throw IllegalArgumentException()

    // 제네릭으로 해결
    val cage2 = Cage2<Carp>()
    cage2.put(Carp("잉어"))

    val carp2: Carp = cage2.getFirst()


    val goldFishCage = Cage2<GoldFish>()
    goldFishCage.put(GoldFish("금붕어"))

    val fishCage = Cage2<Fish>()
    // fishCage.moveFrom(goldFishCage) // 왜 안될까?
}

class Cage {
    private val animals: MutableList<Animal> = mutableListOf()

    fun getFirst(): Animal {
        return animals.first()
    }

    fun put(animal: Animal) {
        this.animals.add(animal)
    }

    fun moveFrom(cage: Cage) {
        this.animals.addAll(cage.animals)
    }
}

// 제네릭
class Cage2<T> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T {
        return animals.first()
    }

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun moveFrom(cage: Cage2<T>) {
        this.animals.addAll(cage.animals)
    }
}
