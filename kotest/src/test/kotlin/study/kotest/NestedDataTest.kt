package study.kotest

import io.kotest.core.spec.style.FunSpec

class NestedDataTest : FunSpec({
    context("each service should support all http methods") {

        val services = listOf(
            "http://internal.foo",
            "http://internal.bar",
            "http://public.baz",
        )

        val methods = listOf("GET", "POST", "PUT")

//        withData(services) { service ->
//            withData(methods) { method ->
//                // test service against method
//            }
//        }

    }
})
