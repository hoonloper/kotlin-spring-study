package generic

fun main() {
    val fishCage = Cage2<Fish>()

    val goldFishCage = Cage2<GoldFish>()
    goldFishCage.put(GoldFish("금붕어"))
    // goldFishCage.moveTo(fishCage) // 타입 미스 매치 에러!!

    /**
     * out - (함수 파라미터 입장에서의) 생산자, 공변
     * in - (함수 파라미터 입장에서의) 소비자, 반공변
     */
    val cage1: Cage2<out Fish> = Cage2<GoldFish>()
    val cage2: Cage2<in GoldFish> = Cage2<Fish>()
}
class Cage2<T : Any> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T {
        return animals.first()
    }

    fun put(animal: T) {
        this.animals.add(animal)
    }

    /**
     * otherCage는 데이터를 꺼내는 getFirst, getAnimals만 사용 가능하다.
     * 즉 데이터를 꺼내는 기능만 사용 가능하다
     * otherCage는 생산자(데이터를 꺼내는) 역할만 한다
     *
     * 생산자 역할만 가능한 이유는 다음과 같다
     * val goldFishCage = Cage2<GoldFish>()
     * goldFishCage.put(GoldFish("금붕어"))
     *
     * val cage2 = Cage2<Fish>()
     * cage2.put(Carp("잉어"))
     * cage.moveFrom(goldFishCage)
     * this -> Cage2<Fish>
     * otherCage -> Cage2<GoldFish>
     * Fish(잉어)를 GoldFish(금붕어) 케이지로 옮기지 못한다!!
     */
    fun moveFrom(otherCage: Cage2<out T>) {
        otherCage.getFirst()
        // otherCage.put(GoldFish("금붕어")) // 에러 발생!!
        this.animals.addAll(otherCage.animals)
    }

    /**
     * 하위 클래스를 상위클래스로 역전시키는 것을 반공변(contra-variant)라고 한다
     * out이 아닌 in을 붙이면 된다!
     *
     * in이 붙은 otherCage는 데이터를 받을 수만 있다!(소비자)
     */
    fun moveTo(otherCage: Cage2<in T>) {
        otherCage.animals.addAll(this.animals)
    }
}
