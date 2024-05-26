package study.kotest

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.testCoroutineScheduler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.days

class CoroutineTest

//class TestDispatcherTest : FunSpec() {
//    init {
//        test("foo").config(coroutineTestScope = true) {
//            println("this test will run with a test dispatcher")
//        }
//    }
//}

//@ExperimentalCoroutinesApi
//@ExperimentalStdlibApi
//class TestDispatcherTest : FunSpec() {
//    init {
//        test("advance time").config(coroutineTestScope = true) {
//            val duration = 1.days
//
//            launch {
//                delay(duration.inWholeMilliseconds)
//            }
//
//            testCoroutineScheduler.advanceTimeBy(duration.inWholeMilliseconds)
//            val currentTime = testCoroutineScheduler.currentTime
//        }
//    }
//}

class TestDispatcherTest : FunSpec() {
    init {
        coroutineTestScope = true
        test("this test uses a test dispatcher") {
        }
        test("and so does this test!") {
        }
    }
}

class ProjectConfig : AbstractProjectConfig() {
    override var coroutineTestScope = true
}
