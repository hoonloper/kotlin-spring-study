package generic

fun main() {
    val fishCage = Cage3<Fish>()
    val animalCage: Cage3<Animal> = fishCage
}

// out을 붙이면 공변하게 되어 소비를 위한 타입을 받을 수 없다
// 즉 생상만 가능하다
// declaration-site variance라고 부른다
// fun moveFrom(cage: Cage2<out T>)와 같이 함수나 변수 지점에 변성을 주는 것은 use-site variance라고 한다
// 코틀린의 List는 자바의 List와 달리 불변 컬렉션이라 데이터를 꺼낼 수만 있다.
class Cage3<out T> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T {
        return this.animals.first()
    }

    fun getAnimals(): List<T> {
        return this.animals
    }
}

interface ListExample<out E> : Collection<E> {
    override val size: Int
    override fun isEmpty(): Boolean
    override fun contains(element: @UnsafeVariance E): Boolean // 타입 안전하다 생각하는 곳에 UnsafeVariance를 적용하면 소비가 가능하다
    override fun iterator(): Iterator<E>
}
