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

