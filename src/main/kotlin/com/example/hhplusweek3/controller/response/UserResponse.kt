package com.example.hhplusweek3.controller.response

import com.example.hhplusweek3.domain.user.User
import java.util.*

data class UserResponse(
    val id: UUID,
    val balance: Double,
) {
    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                id = user.id,
                balance = user.balance,
            )
        }
    }
}
