package annotation_and_reflection

import generic.Animal
import generic.Carp
import generic.GoldFish
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.cast

class TypeSafe

fun main() {
    val cage = TypeSafeCage()
    cage.putOne(Carp::class, Carp("잉어"))
    cage.getOne(Carp::class)
    cage.getOne<Carp>()
    cage.putOne<Carp>(Carp("잉어"))

    val superTypeToken1 = object : SuperTypeToken<List<GoldFish>>() {}
    val superTypeToken2 = object : SuperTypeToken<List<GoldFish>>() {}
    val superTypeToken3 = object : SuperTypeToken<List<Carp>>() {}
    println(superTypeToken2.equals(superTypeToken1))
    println(superTypeToken3.equals(superTypeToken1))

    val superTypeSafeCage = SuperTypeSafeCage()
    superTypeSafeCage.putOne(superTypeToken2, listOf(GoldFish("금붕어1"), GoldFish("금붕어2")))
    val result = superTypeSafeCage.getOne(superTypeToken2)
    println(result)
}

// 타입 안전(타입 안전하게) 이종(두 개 이상의 타입을) 컨테이너
class TypeSafeCage {
    private val animals: MutableMap<KClass<*>, Animal> = mutableMapOf()

    fun <T : Animal> getOne(type: KClass<T>): T {
        return type.cast(animals[type])
    }

    fun <T : Animal> putOne(type: KClass<T>, animal: T) {
        animals[type] = type.cast(animal)
    }

    inline fun <reified T : Animal> getOne(): T {
        return this.getOne(T::class)
    }

    inline fun <reified T : Animal> putOne(animal: T) {
        this.putOne(T::class, animal)
    }
}

// 슈퍼 타입 토큰의 핵심 아이디어
// 제네릭 타입 정보를 리플렉션으로 알아내자!
// List<T> 타입을 저장하면 List와 T를 기억하자!
// SuperTypeToken을 구현한 클래스가 인스턴스화 되자마자
// T 정보를 내부 변수에 저장해버린다.
abstract class SuperTypeToken<T> {
    val type: KType = this::class.supertypes[0].arguments[0].type!! // SuperTypeToken

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as SuperTypeToken<*>

        return type == other.type
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }
}

class SuperTypeSafeCage {
    private val animals: MutableMap<SuperTypeToken<*>, Any> = mutableMapOf()

    fun <T : Any> getOne(token: SuperTypeToken<T>): T {
        return this.animals[token] as T
    }

    fun <T : Any> putOne(token: SuperTypeToken<T>, animal: T) {
        animals[token] = animal
    }
}
