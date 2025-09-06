package core.model

/**
 * data class автоматически генерирует:
 *  - equals()/hashCode()
 *  - toString()
 *  - copy()
 *  - componentN() для деструктуризации
 */
data class User(
    val id: Id,          // строгий тип для идентификатора (см. value class выше)
    val name: String     // обычное неизменяемое (val) поле
)
