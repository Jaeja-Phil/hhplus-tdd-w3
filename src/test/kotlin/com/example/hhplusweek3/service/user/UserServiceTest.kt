package com.example.hhplusweek3.service.user

import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.domain.user.UserCreateObject
import com.example.hhplusweek3.error.BadRequestException
import com.example.hhplusweek3.repository.user.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class UserServiceTest {
    private val userRepository = mockk<UserRepository>()
    private val userService = UserService(userRepository)

    @Test
    fun `createUser - should raise error when balance is negative`() {
        // Given
        val balance = -100.0

        // When & Then
        val error = assertThrows<BadRequestException> {
            userService.createUser(balance)
        }
        assertEquals("Balance should not be negative.", error.message)
    }
    @Test
    fun `createUser - should create User with passed values`() {
        // Given
        val balance = 100.0
        every { userRepository.save(UserCreateObject(balance = balance)) } returns mockk()

        // When
        userService.createUser(balance)

        // Then
        verify { userRepository.save(UserCreateObject(balance = balance)) }
    }

    @Test
    fun `getUserById - should return User if found`() {
        // Given
        val id = UUID.randomUUID()
        val user = User(id, 100.0)
        every { userRepository.findById(id) } returns user

        // When
        val result = userService.getUserById(id)

        // Then
        assertEquals(user, result)
    }

    @Test
    fun `getUserById - should return null if not found`() {
        // Given
        val id = UUID.randomUUID()
        every { userRepository.findById(id) } returns null

        // When
        val result = userService.getUserById(id)

        // Then
        assertNull(result)
    }

    @Test
    fun `adjustUserBalance - should raise error when adjustBalance fails`() {
        // Given
        val amount = -200.0
        val user = mockk<User>() {
            every { adjustBalance(amount) } throws BadRequestException("Not enough balance.")
        }

        // When & Then
        val error = assertThrows<BadRequestException> {
            userService.adjustUserBalance(user, amount)
        }
        assertEquals("Not enough balance.", error.message)
    }

    @Test
    fun `adjustUserBalance - should update balance of User`() {
        // Given
        val amount = 50.0
        val user = mockk<User>() {
            every { adjustBalance(amount) } returns mockk()
        }
        every { userRepository.update(any()) } returns mockk()

        // When
        userService.adjustUserBalance(user, amount)

        // Then
        verify(exactly = 1) { user.adjustBalance(amount) }
        verify(exactly = 1) { userRepository.update(any()) }
    }
}