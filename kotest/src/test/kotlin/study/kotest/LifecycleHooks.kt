package study.kotest

import io.kotest.assertions.fail
import io.kotest.core.spec.BeforeTest
import io.kotest.core.spec.style.WordSpec

class LifecycleHooks

class LifecycleHooksTest : WordSpec({
    beforeTest {
        println("before test $it")
    }
    afterTest { (test, result) ->
        println("after test test=$test, result=$result")
    }
    "테스트" should {
        "실행" {
            println("테스트 실행!")
        }
    }
})

val startTest: BeforeTest = {
    println("before test $it")
}

class FirstTest : WordSpec({
    // startTest 콜백으로 사용
    beforeTest(startTest)

    "테스트" should {
        "실행" {
            println("!!!!")
        }
    }
})

class SecondTest : WordSpec({
    // startTest 콜백으로 사용
    beforeTest(startTest)

    "테스트" should {
        "실패" {
            fail("펑!")
        }
    }
})
