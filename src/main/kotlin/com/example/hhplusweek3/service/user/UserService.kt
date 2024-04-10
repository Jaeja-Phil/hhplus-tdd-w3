package com.example.hhplusweek3.service.user

import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.domain.user.UserCreateObject
import com.example.hhplusweek3.repository.user.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun createUser(balance: Double = 0.0): User {
        return userRepository.save(
            UserCreateObject(balance = balance)
        )
    }

    fun getUserById(id: UUID): User? {
        return userRepository.findById(id)
    }
}