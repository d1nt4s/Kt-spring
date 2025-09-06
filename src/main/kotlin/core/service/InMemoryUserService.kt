package core.service

import core.model.Id
import core.model.User
import core.model.slugify

/**
 * Простая реализация UserService — хранит данные в памяти (в Map).
 */
class InMemoryUserService : UserService {
    private val users = mutableMapOf<Id, User>()

    override fun createUser(name: String): User {
        val id = Id(name.slugify()) // делаем id из имени
        val user = User(id, name)
        users[id] = user
        return user
    }

    override fun findById(id: Id): User? = users[id]

    override fun findAll(): List<User> = users.values.toList()
}
