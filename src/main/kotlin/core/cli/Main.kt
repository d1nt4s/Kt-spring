package core.cli

import core.service.InMemoryUserService
import core.model.slugify

fun main() {
    println("   Hello World!    ".slugify())

    val userService = InMemoryUserService()

    val alice = userService.createUser("Alice Smith")
    val bob = userService.createUser("Bob!")

    println("All users: ${userService.findAll()}")
    println("Find Alice: ${userService.findById(alice.id)}")

    coroutinesDemo()
}