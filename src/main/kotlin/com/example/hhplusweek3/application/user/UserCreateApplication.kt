package com.example.hhplusweek3.application.user

import com.example.hhplusweek3.controller.request.UserCreateRequest
import com.example.hhplusweek3.controller.response.UserResponse
import com.example.hhplusweek3.service.user.UserService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class UserCreateApplication(
    private val userService: UserService
) {
    fun run(request: UserCreateRequest): UserResponse {
        val createdUser = userService.createUser(balance = request.balance)
        return UserResponse.from(createdUser)
    }
}