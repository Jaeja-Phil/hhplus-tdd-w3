package com.example.hhplusweek3.entity.user

import com.example.hhplusweek3.domain.User
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID? = null,
    val balance: Double = 0.0
) {
    fun toDomain(): User {
        return User(
            id = requireNotNull(id),
            balance = balance,
        )
    }
}
