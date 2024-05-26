package study.kotest

import io.kotest.core.annotation.AutoScan
import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.BeforeProjectListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.WordSpec

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
