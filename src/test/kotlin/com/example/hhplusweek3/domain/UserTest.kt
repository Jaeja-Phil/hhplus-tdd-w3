package com.example.hhplusweek3.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class UserTest {
    @Test
    fun `toEntity - should return UserEntity`() {
        // Given
        val user = User(
            id = UUID.randomUUID(),
            balance = 100.0,
        )

        // When
        val result = user.toEntity()

        // Then
        assertEquals(user.id, result.id)
        assertEquals(user.balance, result.balance)
    }
}