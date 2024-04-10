package com.example.hhplusweek3.controller.response

import com.example.hhplusweek3.domain.user.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class UserResponseTest {
    @Test
    fun `from - should return UserResponse with passed values`() {
        // Given
        val user = User(id = UUID.randomUUID(), balance = 100.0)

        // When
        val result = UserResponse.from(user)

        // Then
        assertEquals(user.id, result.id)
        assertEquals(user.balance, result.balance)
    }
}