package com.example.hhplusweek3.controller

import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.domain.userQueueToken.UserQueueToken
import com.example.hhplusweek3.error.InternalServerError
import com.example.hhplusweek3.error.NotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired

open class BaseController {
    @Autowired
    lateinit var request: HttpServletRequest

    fun currentUserQueueToken(): UserQueueToken {
        return when (val currentUserQueueTokenAttribute = request.getAttribute("currentUserQueueToken")) {
            null -> throw NotFoundException("Current user queue token is not found.")
            !is UserQueueToken -> throw InternalServerError("Current user queue token is invalid.")
            else -> currentUserQueueTokenAttribute
        }
    }

    fun currentUser(): User {
        return currentUserQueueToken().user
    }
}