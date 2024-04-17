package com.example.hhplusweek3.application.user

import com.example.hhplusweek3.controller.response.UserResponse
import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.error.NotFoundException
import com.example.hhplusweek3.domain.user.UserDomain
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class UserAdjustBalanceApplicationTest {
    private val userDomain = mockk<UserDomain>()
    private val userAdjustBalanceApplication = UserAdjustBalanceApplication(userDomain)

    @Test
    fun `run - should raise error when user not found`() {
        // Given
        val userId = UUID.randomUUID()
        val amount = 100.0
        every { userDomain.getUserById(userId) } returns null

        // When & Then
        val error = assertThrows<NotFoundException> {
            userAdjustBalanceApplication.run(userId, amount)
        }
        assertEquals("User not found.", error.message)
    }

    @Test
    fun `run - should raise error when adjusting balance fails`() {
        // Given
        val userId = UUID.randomUUID()
        val amount = 100.0
        val user = mockk<User>()
        every { userDomain.getUserById(userId) } returns user
        every { userDomain.adjustUserBalance(user, amount) } throws RuntimeException("Adjusting balance failed.")

        // When & Then
        val error = assertThrows<RuntimeException> {
            userAdjustBalanceApplication.run(userId, amount)
        }
        assertEquals("Adjusting balance failed.", error.message)
    }

    @Test
    fun `run - should return UserResponse with updated user`() {
        // Given
        val userId = UUID.randomUUID()
        val amount = 100.0
        val user = mockk<User>() {
            every { id } returns userId
        }
        val updatedUser = mockk<User>() {
            every { id } returns userId
            every { balance } returns 100.0
        }
        every { userDomain.getUserById(userId) } returns user
        every { userDomain.adjustUserBalance(user, amount) } returns updatedUser

        // When
        val result = userAdjustBalanceApplication.run(userId, amount)

        // Then
        assert(result is UserResponse)
        assertEquals(userId, result.id)
        assertEquals(100.0, result.balance)
    }
}