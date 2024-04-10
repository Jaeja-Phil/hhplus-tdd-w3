package com.example.hhplusweek3.entity.user

import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.domain.user.UserCreateObject
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

    companion object {
        fun fromCreateObject(userCreateObject: UserCreateObject): UserEntity {
            return UserEntity(
                balance = userCreateObject.balance,
            )
        }
    }
}
