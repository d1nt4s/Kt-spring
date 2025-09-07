package core.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class StringsTest {
    @ParameterizedTest
    @CsvSource(
        "   Hello World   , hello-world",
        "Bob!, bob",
        "'', ''",
        "'---ABC---', abc"
    )
    fun `slugify cases`(input: String, expected: String) {
        assertEquals(expected, input.slugify())
    }

    @Test
    fun `slugify trims and lowers`() {
        assertEquals("abc", "  ABC ".slugify())
    }
}
