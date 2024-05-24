package study.kotest

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.WordSpec
import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

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

class InstancePerTest : WordSpec({
    isolationMode = IsolationMode.InstancePerTest

    "a" should {
        println("Hello")
        "b" {
            println("From")
        }
        "c" {
            println("Sam")
        }
    }
})

class InstancePerTest2 : WordSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerTest

    init {
        "a" should {
            println("Hello")
            "b" {
                println("From")
            }
            "c" {
                println("Sam")
            }
        }
    }
}

class InstancePerTest3 : WordSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerTest

    val counter = AtomicInteger(0)

    init {
        "a" should {
            println("a=" + counter.getAndIncrement())
            "b" {
                println("b=" + counter.getAndIncrement())
            }
            "c" {
                println("c=" + counter.getAndIncrement())
            }
        }
    }
}

class SingleInstanceTest2 : WordSpec() {
    val counter = AtomicInteger(0)

    init {
        "a" should {
            println("a=" + counter.getAndIncrement())
            "b" {
                println("b=" + counter.getAndIncrement())
            }
            "c" {
                println("c=" + counter.getAndIncrement())
            }
        }
    }
}
