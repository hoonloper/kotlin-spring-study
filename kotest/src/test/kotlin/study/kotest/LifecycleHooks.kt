package study.kotest

import io.kotest.assertions.fail
import io.kotest.core.listeners.*
import io.kotest.core.spec.BeforeTest
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.WordSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import kotlin.reflect.KClass

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

class OverridingTest : WordSpec() {
    override suspend fun beforeTest(testCase: TestCase) {
        println("before test $testCase")
    }

    init {
        "테스트" should {
            "실행" {
                println("!!!!")
            }
        }
    }
}

class CallbackTest : StringSpec({
    beforeContainer {
        println("beforeContainer: $it")
    }

    afterContainer { (testCase, result) ->
        println("afterContainer: $testCase 결과: $result")
    }

    beforeEach {
        println("beforeEach: $it")
    }

    afterEach { (testCase, result) ->
        println("afterEach: $testCase 결과: $result")
    }

    beforeAny {
        println("beforeAny: $it")
    }

    afterAny { (testCase, result) ->
        println("afterAny: $testCase 결과: $result")
    }

    beforeTest {
        println("beforeTest: $it")
    }

    afterTest { (testCase, result) ->
        println("afterTest: $testCase 결과: $result")
    }

    beforeSpec {
        println("beforeSpec: ${it::class.simpleName}")
    }

    afterSpec {
        println("afterSpec: ${it::class.simpleName}")
    }

    "example test 1" {
        println("테스트 1 실행 중")
    }

    "example test 2" {
        println("테스트 2 실행 중")
    }
}) {
    init {
        listener(MyTestListener)
    }

    object MyTestListener : TestListener {
        override suspend fun prepareSpec(kclass: KClass<out Spec>) {
            println("prepareSpec: ${kclass.simpleName}")
        }

        override suspend fun finalizeSpec(
            kclass: KClass<out Spec>,
            results: Map<TestCase, TestResult>,
        ) {
            println("finalizeSpec: ${kclass::class.simpleName}")
            results.forEach { (testCase, result) ->
                println("테스트 결과: $testCase: $result")
            }
        }

        override suspend fun beforeInvocation(
            testCase: TestCase,
            iteration: Int,
        ) {
            println("beforeInvocation: $testCase, 반복: $iteration")
        }

        override suspend fun afterInvocation(
            testCase: TestCase,
            iteration: Int,
        ) {
            println("afterInvocation: $testCase, 반복: $iteration")
        }
    }
}

