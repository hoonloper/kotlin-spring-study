package study.kotest;

import io.kotest.core.annotation.EnabledCondition
import io.kotest.core.annotation.EnabledIf
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.Enabled
//import io.kotest.core.test.EnabledIf
import io.kotest.core.test.TestCase
import kotlin.reflect.KClass

class ConditionalEvaluationTest {}

class EnabledTest : StringSpec({
    "테스트".config(enabled = false) {
        // ...
    }

    val isLinux = true
    "리눅스 테스트".config(enabled = isLinux) {
        // ...
    }
})

class EnabledIfTest : StringSpec({
    val disableSkip: EnabledIf = { !it.name.testName.startsWith("skip") }

    "skip 테스트".config(enabledIf = disableSkip) {
        // ...
    }

    "run 테스트".config(enabledIf = disableSkip) {
        // ...
    }
})

class EnabledOrReasonIfTest : StringSpec({
    val disableDangerOnFridays: (TestCase) -> Enabled = {
        if (it.name.testName.startsWith("skip")) {
            Enabled.disabled("skip으로 시작해서 테스트 실행 안함")
        } else {
            Enabled.enabled
        }
    }

    "skip 테스트".config(enabledOrReasonIf = disableDangerOnFridays) {
        // test here
    }

    "run 테스트".config(enabledOrReasonIf = disableDangerOnFridays) {
        // test here
    }
})

class FocusStringTest : StringSpec({
    "test 1" {
        // skipped
    }

    "f:test 2" {
        // executed
    }

    "test 3" {
        // skipped
    }
})

class FocusFunTest : FunSpec({
    context("test 1") {
        // skipped
        test("foo") {
            // skipped
        }
    }

    context("f:test 2") {
        // executed
        test("foo") {
            // executed
        }
    }

    context("test 3") {
        // skipped
        test("foo") {
            // skipped
        }
    }
})

class BangTest : StringSpec({
    "!test 1" {
        // skipped
    }

    "test 2" {
        // executed
    }

    "test 3" {
        // executed
    }
})

class LinuxOnlyCondition : EnabledCondition {
    val IS_OS_LINUX = true

    override fun enabled(kclass: KClass<out Spec>): Boolean = when {
        kclass.simpleName?.contains("Linux") == true -> IS_OS_LINUX
        else -> true // non Linux tests always run
    }
}

@EnabledIf(LinuxOnlyCondition::class)
class MyLinuxTest1 : FunSpec() {
    // tests here
}

@EnabledIf(LinuxOnlyCondition::class)
class MyLinuxTest2 : DescribeSpec() {
    // tests here
}

@EnabledIf(LinuxOnlyCondition::class)
class MyWindowsTests : DescribeSpec() {
    // tests here
}