package com.example.hhplusweek3.application.user

import com.example.hhplusweek3.controller.response.UserResponse
import com.example.hhplusweek3.error.NotFoundException
import com.example.hhplusweek3.service.user.UserService
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserAdjustBalanceApplication(
    private val userService: UserService
) {
    fun run(userId: UUID, amount: Double): UserResponse {
        val user = userService.getUserById(userId) ?: throw NotFoundException("User not found.")
        val updatedUser = userService.adjustUserBalance(user, amount)
        return UserResponse.from(updatedUser)
    }
}