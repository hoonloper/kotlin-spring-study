package lateinit_lazy

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PersonTest {
    private val person = Person()

    @Test
    fun isKimTest() {
        // given
        val person = person.apply { "김수한무" }

        // when & then
        assertEquals(person.isKim, true)
    }

    @Test
    fun maskingNameTest() {
        // given
        val person = person.apply { "홍길동" }

        // when & then
        assertEquals(person.maskingName, "홍**")
    }
}