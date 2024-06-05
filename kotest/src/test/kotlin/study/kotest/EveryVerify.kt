package study.kotest

import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class DataKlass(
    val a: Long,
    val b: Long
)

class Calculator {
    fun add(
        a: Long,
        b: Long,
    ) = a + b
}

class EveryVerify : DescribeSpec({
    val data =
        mockk<DataKlass> {
            every { a } returns 1L
            every { b } returns 2L
        }
    val mockedCalculator = mockk<Calculator>()

    every { mockedCalculator.add(data.a, data.b) } returns 3

    val result = mockedCalculator.add(data.a, data.b)

    verify { mockedCalculator.add(data.a, data.b) }
})
