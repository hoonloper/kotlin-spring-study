package study.kotest

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TestingStyles

class FunSpecTest : FunSpec({
    test("1 + 1 = 2") {
        1 + 1 shouldBe 2
    }

    context("1과 1을 더하면") {
        test("2가 된다") {
            1 + 1 shouldBe 2
        }
    }
    xcontext("context 실행 X") {
        // ...
    }
    xtest("test 실행 X") {
        // TODO로 남겨두거나 깨지는 테스트 일시적 비활성화로 좋을듯!
    }
})

class StringSpecTest : StringSpec({
    "1 + 1 = 2" {
        1 + 1 shouldBe 2
    }
})
