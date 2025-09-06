package core.model

/**
 * Value class: лёгкая обёртка над строкой, но тип — отдельный.
 * На JVM компилятор старается не выделять объект (инлайнит), когда это безопасно.
 */
@JvmInline
value class Id(val value: String) {
    init {
        // Базовая валидация: запрещаем пустые ID сразу на создании.
        require(value.isNotBlank()) { "Id must be non-blank" }
    }

    override fun toString(): String = value
}
