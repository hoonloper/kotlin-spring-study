package study.kotest

import io.kotest.core.annotation.AutoScan
import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.BeforeProjectListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.WordSpec
import io.kotest.extensions.system.NoSystemOutListener

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
