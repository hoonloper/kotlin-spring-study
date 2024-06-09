package annotation_and_reflection

import kotlin.reflect.KClass

class Annotation

// SOURCE: 컴파일 때만 존재
// BINARY: 런타임 때도 있지만 리플렉션을 쓸 수 없다
// RUNTIME: 런타임 때도 있지만 리플렉션을 쓸 수 있다
@Retention(AnnotationRetention.RUNTIME) // default
@Target(AnnotationTarget.CLASS) // 붙일 수 있는 곳
@Repeatable
annotation class Shape(
    val text: String,
    val texts: Array<String>,
    val number: Int,
    val clazz: KClass<*>, // 코드로 작성한 클래스를 표현한 클래스
)

@Shape(text = "A", texts = ["A", "B"], number = 25, clazz = Annotation::class)
class Annotations

// 특정 getter에 어노테이션을 붙인다는 의미
// use-site target
// param -> property -> field 순서로 우선순위를 가짐
// @Target을 지정하고 있다면 해당 Target에 붙음
// class Hello(@get:Shape val name: String)

// Repeatable 어노테이션
// @Repeatable 어노테이션이 붙어 있으면 여러개를 붙일 수 있다
@Shape(text = "A", texts = ["A", "B"], number = 25, clazz = Annotation::class)
@Shape(text = "A", texts = ["A", "B"], number = 25, clazz = Annotation::class)
class Hello

fun main() {
    val clazz: KClass<Annotation> = Annotation::class
}
