package com.example.hhplusweek3.application.userQueueToken

import com.example.hhplusweek3.controller.response.TokenResponse
import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.error.NotFoundException
import com.example.hhplusweek3.domain.user.UserDomain
import com.example.hhplusweek3.service.userQueueToken.UserQueueTokenService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class UserQueueTokenCreateApplicationTest {
    private val userQueueTokenService = mockk<UserQueueTokenService>()
    private val userDomain = mockk<UserDomain>()
    private val SUT = UserQueueTokenCreateApplication(userQueueTokenService, userDomain)

    @Test
    fun `run - should raise error when user not found`() {
        // Given
        val userId = UUID.randomUUID()
        every { userDomain.getUserById(userId) } returns null

        // When
        val exception = assertThrows<NotFoundException> {
            SUT.run(userId)
        }

        // Then
        assertEquals("User not found.", exception.message)
    }

    @Test
    fun `run - should raise error when creating user queue token fails`() {
        // Given
        val userId = UUID.randomUUID()
        val user = mockk<User>()
        every { userDomain.getUserById(userId) } returns user
        every { userQueueTokenService.createUserQueueTokenWithValidation(user) } throws
                RuntimeException("some error inside")

        // When
        val exception = assertThrows<RuntimeException> {
            SUT.run(userId)
        }

        // Then
        assertEquals("some error inside", exception.message)
    }

    @Test
    fun `run - should return with TokenResponse`() {
        // Given
        val userId = UUID.randomUUID()
        val user = mockk<User>()
        val expectedToken = "token"
        every { userDomain.getUserById(userId) } returns user
        every { userQueueTokenService.createUserQueueTokenWithValidation(user) } returns mockk {
            every { token } returns expectedToken
        }

        // When
        val result = SUT.run(userId)

        // Then
        assertEquals(TokenResponse(token = expectedToken), result)
    }
}