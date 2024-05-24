package study.kotest;

import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.Enabled
import io.kotest.core.test.EnabledIf
import io.kotest.core.test.TestCase

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
