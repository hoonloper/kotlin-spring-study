package study.kotest

import io.kotest.core.annotation.AutoScan
import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.core.listeners.*
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.WordSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.system.NoSystemOutListener
import io.kotest.matchers.shouldBe

class ExtensionsTest

class MyTestListener : BeforeSpecListener, AfterSpecListener {
    override suspend fun beforeSpec(spec: Spec) {
        println("kafka 연동")
    }

    override suspend fun afterSpec(spec: Spec) {
        println("kafka 연동 해제")
    }
}

class ExtensionTest : WordSpec({
    extension(MyTestListener())

    "비즈니스 로직 실행" `when` {
        println("실행 완료!")
    }
})

@AutoScan
object MyProjectListener : BeforeProjectListener, AfterProjectListener {
    override suspend fun beforeProject() {
        println("Project starting")
    }

    override suspend fun afterProject() {
        println("Project complete")
    }
}

class NoSystemOutListenerTest : DescribeSpec({
    listener(NoSystemOutListener)

    describe("모든 테스트는 System out 사용하지 않아야 한다") {
        it("사용하게 되면") {
            println("실패한다!") // failure
        }
    }
})

object TimerListener : BeforeTestListener, AfterTestListener {
    private var started = 0L

    override suspend fun beforeTest(testCase: TestCase) {
        started = System.currentTimeMillis()
    }

    override suspend fun afterTest(
        testCase: TestCase,
        result: TestResult,
    ) {
        println("Duration of ${testCase.descriptor}" + "\ntime = " + (System.currentTimeMillis() - started))
    }
}

class TimerListenerTest : FunSpec({
    extensions(TimerListener)

    test("1_000_000번 문자열을 더하면 정상적으로 더해진다.") {
        val len = 1_000_000
        val str = "a"

        var result = ""
        for (i: Int in 1..len) {
            result += str
        }

        result shouldBe str.repeat(len)
    }
})

object MyConfig : AbstractProjectConfig() {
    override fun extensions(): List<Extension> = listOf(TimerListener)
}
