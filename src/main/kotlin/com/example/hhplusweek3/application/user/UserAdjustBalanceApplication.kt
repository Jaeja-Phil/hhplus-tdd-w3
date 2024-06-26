package com.example.hhplusweek3.application.user

import com.example.hhplusweek3.controller.response.UserResponse
import com.example.hhplusweek3.error.NotFoundException
import com.example.hhplusweek3.domain.user.UserDomain
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserAdjustBalanceApplication(
    private val userDomain: UserDomain
) {
    fun run(userId: UUID, amount: Double): UserResponse {
        val user = userDomain.getUserById(userId) ?: throw NotFoundException("User not found.")
        val updatedUser = userDomain.adjustUserBalance(user, amount)
        return UserResponse.from(updatedUser)
    }
}