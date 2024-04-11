package com.example.hhplusweek3.controller

import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.error.InternalServerError
import com.example.hhplusweek3.error.NotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired

open class BaseController {
    @Autowired
    lateinit var request: HttpServletRequest
    fun currentUser(): User {
        return when (val currentUserAttribute = request.getAttribute("currentUser")) {
            null -> throw NotFoundException("Current user is not found.")
            !is User -> throw InternalServerError("Current user is invalid.")
            else -> currentUserAttribute
        }
    }
}