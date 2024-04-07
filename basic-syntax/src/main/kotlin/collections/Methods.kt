package collections

val numbers = listOf(1, -2, 3, -4, 5, -6)

// filter
val positives = numbers.filter { x -> x > 0 }
val negatives = numbers.filter { it < 0 }

// map
val doubled = numbers.map { x -> x * 2 }
val tripled = numbers.map { it * 3 }

// any, all, none
val anyNegative = numbers.any { it < 0 }
val anyGT6 = numbers.any { it > 6 }

// all
val allEven = numbers.all { it % 2 == 0}
val allLess6 = negatives.all { it < 6 }

// none
val allNoneEven = numbers.none { it % 2 == 1 }
val allNoneLess6 = numbers.none { it > 6 }

// fin, findLast
val words = listOf("Lets", "find", "something", "in", "collection", "somehow")

val first = words.find { it.startsWith("some") }
val last = words.findLast { it.startsWith("some") }
val nothing = words.find { it.contains("nothing") }

// first, last
val firstNum = numbers.first()
val lastNum = numbers.last()

val firstEven = numbers.first { it % 2 == 0 }
val lastOdd = numbers.last { it % 2 == 1 }

// firstOrNull, lastOrNull
val wordList = listOf("foo", "bar", "baz", "faz")
val empty = emptyList<String>()

val firstOrNull = empty.firstOrNull()
val lastOrNull = empty.lastOrNull()

val firstF = words.firstOrNull { it.startsWith('f') }
val firstZ = words.firstOrNull { it.startsWith('z') }
val lastF = words.lastOrNull { it.endsWith('f') }
val lastZ = words.lastOrNull { it.endsWith('z') }

// count
val totalCount = numbers.count()
val evenCount = numbers.count { it % 2 == 0 }

// associateBy, groupBy
data class Person(val name: String, val city: String, val phone: String)

val people = listOf(
    Person("John", "Boston", "+1-888-123456"),
    Person("Sarah", "Munich", "+49-777-789123"),
    Person("Svyatoslav", "Saint-Petersburg", "+7-999-456789"),
    Person("Vasilisa", "Saint-Petersburg", "+7-999-123456")
)

val phoneBook = people.associateBy { it.phone }
val cityBook = people.associateBy(Person::phone, Person::city)
val peopleCities = people.groupBy(Person::city, Person::name)
val lastPersonCity = people.associateBy(Person::city, Person::name)

// partition
val evenOdd = numbers.partition { it % 2 == 0 }
//val (positives, negatives) = numbers.partition { it > 0 }

// flatMap
val fruitsBag = listOf("apple", "orange", "banana", "grapes")
val clothesBag = listOf("shirts", "pants", "jeans")
val cart = listOf(fruitsBag, clothesBag)
val mapBag = cart.map { it }
val flatMapBag = cart.flatMap { it } // or cart.flatten()

// minOrNull, maxOrNull
val emptyNum = emptyList<Int>()
val only = listOf(3)

// sorted
val shuffled = listOf(5, 4, 2, 1, 3, -10)
val natural = shuffled.sorted()
val inverted = shuffled.sortedBy { -it }
val descending = shuffled.sortedDescending()
val descendingBy = shuffled.sortedByDescending { -it }

// zip
val A = listOf("a", "b", "c")
val B = listOf(1, 2, 3, 4)

val resultPairs = A zip B
val resultReduce = A.zip(B) { a, b -> "$a$b" }

// getOrElse
val list = listOf(0, 10, 20)

val map2 = mutableMapOf<String, Int?>()

fun main() {
    // minOrNull, maxOrNull
    println("Numbers: $numbers, min = ${numbers.minOrNull()} max = ${numbers.maxOrNull()}")
    println("Empty: $emptyNum, min = ${emptyNum.minOrNull()}, max = ${emptyNum.maxOrNull()}")
    println("Only: $only, min = ${only.minOrNull()}, max = ${only.maxOrNull()}")

    // getOrElse
    println(list.getOrElse(1) { 42 })
    println(list.getOrElse(10) { 42 })

    val key = "x"
    println(map2.getOrElse(key) { 1 })

    map2[key] = 3
    println(map2.getOrElse(key) { 1 })

    map2[key] = null
    println(map2.getOrElse(key) { 1 })
}