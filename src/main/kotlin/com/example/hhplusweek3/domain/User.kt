package com.example.hhplusweek3.domain

import com.example.hhplusweek3.entity.user.UserEntity
import java.util.*

data class User(
    val id: UUID,
    val balance: Double,
) {
    fun toEntity(): UserEntity {
        return UserEntity(
            id = id,
            balance = balance,
        )
    }
}
