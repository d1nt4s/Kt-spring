package core.service

import core.model.Id
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class InMemoryUserServiceTest {
    @Test
    fun `create and find`() {
        val svc = InMemoryUserService()
        val alice = svc.createUser("Alice Smith")

        assertEquals("alice-smith", alice.id.value)
        assertEquals(alice, svc.findById(Id("alice-smith")))
        assertEquals(1, svc.findAll().size)
    }
}
