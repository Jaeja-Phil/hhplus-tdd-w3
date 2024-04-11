package com.example.hhplusweek3.domain

import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.error.NotEnoughBalanceException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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

    @Test
    fun `adjustBalance - should raise NotEnoughBalanceException if balance is not enough`() {
        // Given
        val user = User(
            id = UUID.randomUUID(),
            balance = 100.0,
        )

        // When
        val exception = assertThrows<NotEnoughBalanceException> {
            user.adjustBalance(-200.0)
        }

        // Then
        assertEquals("Not enough balance.", exception.message)
    }

    @Test
    fun `adjustBalance - should return User with updated balance`() {
        // Given
        val user = User(
            id = UUID.randomUUID(),
            balance = 100.0,
        )

        // When
        val result = user.adjustBalance(-50.0)

        // Then
        assertEquals(user.id, result.id)
        assertEquals(50.0, result.balance)
    }
}