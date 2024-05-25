package study.kotest

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
