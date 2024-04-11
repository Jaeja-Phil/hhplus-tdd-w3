package com.example.hhplusweek3.domain.user

import com.example.hhplusweek3.entity.user.UserEntity
import com.example.hhplusweek3.error.NotEnoughBalanceException
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

    fun adjustBalance(amount: Double): User {
        val updatedAmount = balance + amount
        if (updatedAmount < 0) {
            throw NotEnoughBalanceException("Not enough balance.")
        }

        return copy(balance = updatedAmount)
    }
}
