package study.kotest

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.WordSpec
import java.util.UUID

class IsolationModes()

class InnerTest : WordSpec({
    isolationMode = IsolationMode.SingleInstance
    // tests here
})

class OuterTest : WordSpec() {
    override fun isolationMode() = IsolationMode.SingleInstance
    init {
        // tests here
    }
}

class SingleInstanceTest : WordSpec({
    val id = UUID.randomUUID()
    "a" should {
        println(id)
        "b" {
            println(id)
        }
        "c" {
            println(id)
        }
    }
})

