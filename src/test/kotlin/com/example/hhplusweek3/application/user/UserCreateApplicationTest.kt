package com.example.hhplusweek3.application.user

import com.example.hhplusweek3.controller.request.UserCreateRequest
import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.domain.user.UserDomain
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class UserCreateApplicationTest {
    private val userDomain = mockk<UserDomain>()
    private val userCreateApplication = UserCreateApplication(userDomain)

    @Test
    fun `run - should raise error when creating user fails`() {
        // Given
        val balance = 100.0
        val request = UserCreateRequest(balance = balance)
        val errorMessage = "Failed to create user"
        every { userDomain.createUser(balance) } throws RuntimeException(errorMessage)

        // When & Then
        val error = assertThrows<RuntimeException> {
            userCreateApplication.run(request)
        }
        assertEquals(errorMessage, error.message)
    }

    @Test
    fun `run - should create User with passed values`() {
        // Given
        val balance = 100.0
        val user = User(id = UUID.randomUUID(), balance = balance)
        val request = UserCreateRequest(balance = balance)
        every { userDomain.createUser(balance) } returns user

        // When
        val result = userCreateApplication.run(request)

        // Then
        assertEquals(user.id, result.id)
        assertEquals(user.balance, result.balance)
        verify { userDomain.createUser(balance) }
    }
}