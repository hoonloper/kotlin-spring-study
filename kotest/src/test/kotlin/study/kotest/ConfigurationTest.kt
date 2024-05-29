package study.kotest

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.WordSpec
import io.kotest.core.test.config.TestCaseConfig
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.seconds

class ConfigurationTest

class MyTests : ShouldSpec() {
    init {
        should("return the length of the string").config(invocations = 10, threads = 2) {
            "sammy".length shouldBe 5
            "".length shouldBe 0
        }
    }
}

class MyTests2 : WordSpec() {
    init {
        "String.length" should {
            "return the length of the string".config(timeout = 2.seconds) {
                "sammy".length shouldBe 5
                "".length shouldBe 0
            }
        }
    }
}

class FunTest : FunSpec() {
    init {
        test("FunSpec should support config syntax").config(tags = setOf()) {
            // ...
        }
    }
}

class MyTest3 : StringSpec() {
    override fun defaultTestCaseConfig() = TestCaseConfig(invocations = 3)

    init {
        // your test cases ...
    }
}

class FunTest2 : FunSpec() {
    init {

        defaultTestConfig = TestCaseConfig(enabled = true, invocations = 3)

        test("FunSpec should support Spec config syntax in init{} block") {
            // ...
        }
    }
}
