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
    // *** 타입 파라미터에 넣은 클래스가 상속 관계에 있더라도 제네릭 클래스로 들어가면 상속 관계는 무시한다. ***
    // 이것이 무공변 (in-variant, 불공변)이라 한다.

    // Java의 배열은 하위 타입이 상위 타입이 될 수 있다.
    // String[] -> Object[] - 이것은 공변(co-variant)이라 한다.
    /**
     * String[] strs = new String[]{"A", "B", "C"};
     * Object[] objs = strs;
     * objs[0] = 1; // java.lang.ArrayStoreException: java.lang.Integer
     * 공변하기 때문에 다른 타입을 넣을 수 있다.
     * 그러나 이것은 런타임 환경에서 잡히기 때문에 매우 위험하다. (컴파일에서 잡히는 게 좋다)
     *
     * List<String> strs = List.of("A", "B", "C")
     * List<Object> objs = strs // 에러발생!! List 제네릭은 무공변하게 만들었다!
     */

    // 제네릭 앞에 out을 붙여줌으로써 무공변에 변성을 준다.
    // 변성을 주었기에 variance(변성) annotation이라 한다.
    fishCage.moveFrom(goldFishCage)
    val fish: Fish = fishCage.getFirst()
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
//class Cage2<T> {
//    private val animals: MutableList<T> = mutableListOf()
//
//    fun getFirst(): T {
//        return animals.first()
//    }
//
//    fun put(animal: T) {
//        this.animals.add(animal)
//    }
//
//    fun moveFrom(cage: Cage2<T>) {
//        this.animals.addAll(cage.animals)
//    }
//}
