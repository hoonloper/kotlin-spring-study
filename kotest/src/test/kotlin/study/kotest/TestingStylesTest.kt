package study.kotest

import io.kotest.core.spec.style.*
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

class ShouldSpecTest : ShouldSpec({
    should("1 + 1 = 2") {
        1 + 1 shouldBe 2
    }

    context("1과 1을 더하면") {
        should("2가 된다") {
            1 + 1 shouldBe 2
        }
    }
    xcontext("context 실행 X") {
        // ...
    }
    xshould("should 실행 X") {
        // ...
    }
})

class DescribeSpecTest : DescribeSpec({
    describe("더하기") {
        context("1 + 1") {
            it("= 2") {
                1 + 1 shouldBe 2
            }
        }
    }

    xdescribe("describe 실행 x") {
        // ...
    }
    xcontext("context 실행 X") {
        // ...
    }
    xit("should 실행 X") {
        // ...
    }
})

class BehaviorSpecTest : BehaviorSpec({
    given("1과 1이") {
        val one1 = 1
        val one2 = 1
        and("주어지고") {
            `when`("그 둘을 더하려고") {
                and("하고") {
                    then("결과는 2가 된다") {
                        one1 + one2 shouldBe 2
                    }
                }
            }
        }
    }
    Given("1") {
        And("2") {
            When("3") {
                And("4") {
                    Then("5") {
                        // ...
                    }
                }
            }
        }
    }
})

class WordSpecTest : WordSpec({
    "2" When {
        "1 + 1" should {
            "return 2" {
                1 + 1 shouldBe 2
            }
        }
    }
})

class FreeSpecTest : FreeSpec({
    "1 더하기 1은" - {
        "2가 나오길 기대하고 있다" {
            1 + 1 shouldBe 2
        }
    }
})

class FeatureSpecTest : FeatureSpec({
    feature("더하기") {
        scenario("1과 1을 더하면 2가 된다") {
            1 + 1 shouldBe 2
        }
    }

    xfeature("feature 실행 X") {
        xscenario("xscenario 실행 X") {
            // ...
        }
    }
})
