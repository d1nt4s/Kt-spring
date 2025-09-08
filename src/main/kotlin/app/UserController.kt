package app

import core.model.Id
import core.model.User
import core.service.InMemoryUserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: InMemoryUserService = InMemoryUserService()
) {
    data class CreateUserRequest(val name: String)

    @PostMapping
    fun create(@RequestBody body: CreateUserRequest): User =
        userService.createUser(body.name)

    @GetMapping
    fun list(): List<User> = userService.findAll()

    @GetMapping("/{id}")
    fun byId(@PathVariable id: String): User? =
        userService.findById(Id(id))
}
