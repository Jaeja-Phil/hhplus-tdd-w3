package com.example.hhplusweek3.application.userQueueToken

import com.example.hhplusweek3.controller.response.TokenResponse
import com.example.hhplusweek3.error.NotFoundException
import com.example.hhplusweek3.service.user.UserService
import com.example.hhplusweek3.service.userQueueToken.UserQueueTokenService
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserQueueTokenCreateApplication(
    private val userQueueTokenService: UserQueueTokenService,
    private val userService: UserService
) {
    fun run(userId: UUID): TokenResponse {
        val user = userService.getUserById(userId) ?: throw NotFoundException("User not found.")
        val userQueueToken = userQueueTokenService.createUserQueueTokenWithValidation(user)
        return TokenResponse(token = userQueueToken.token)
    }
}