package core.service

import core.model.Id
import core.model.User

/**
 * Интерфейс сервиса работы с пользователями.
 */
interface UserService {
    fun createUser(name: String): User
    fun findById(id: Id): User?
    fun findAll(): List<User>
}
