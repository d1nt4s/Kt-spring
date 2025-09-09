package app.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @Column(name = "id", nullable = false, length = 128)
    var id: String = "",

    @Column(name = "name", nullable = false, length = 255)
    var name: String = ""
)
