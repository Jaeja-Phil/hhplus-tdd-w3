package com.example.hhplusweek3.domain.userQueueToken

import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.domain.userQueueToken.UserQueueTokenDomain
import com.example.hhplusweek3.error.BadRequestException
import com.example.hhplusweek3.repository.userQueueToken.UserQueueTokenRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class UserQueueTokenDomainTest {
    private val userQueueTokenRepository = mockk<UserQueueTokenRepository>()
    private val userQueueTokenDomain = spyk(UserQueueTokenDomain(userQueueTokenRepository))

    @Test
    fun `createUserQueueTokenWithValidation - should create user queue token with validation`() {
        // Given
        val user = User(id = UUID.randomUUID(), balance = 100.0)
        justRun { userQueueTokenDomain.validateUserForTokenCreation(user) }
        every { userQueueTokenRepository.save(any()) } returns mockk()

        // When
        userQueueTokenDomain.createUserQueueTokenWithValidation(user)

        // Then
        verify(exactly = 1) { userQueueTokenDomain.validateUserForTokenCreation(user) }
        verify(exactly = 1) { userQueueTokenRepository.save(any()) }
        verifyOrder {
            userQueueTokenDomain.validateUserForTokenCreation(user)
            userQueueTokenRepository.save(any())
        }
    }

    @Test
    fun `validateUserForTokenCreation - should throw BadRequestException if user is not valid for token creation`() {
        // Given
        val user = User(id = UUID.randomUUID(), balance = 100.0)
        every { userQueueTokenRepository.findByUserIdAndStatusNot(any(), any()) } returns listOf(mockk())

        // When
        val exception = assertThrows<BadRequestException> {
            userQueueTokenDomain.validateUserForTokenCreation(user)
        }

        // Then
        assertEquals("User is not valid for token creation.", exception.message)
        verify(exactly = 1) { userQueueTokenRepository.findByUserIdAndStatusNot(user.id, any()) }
    }

    @Test
    fun `validateUserForTokenCreation - should not throw BadRequestException if user is valid for token creation`() {
        // Given
        val user = User(id = UUID.randomUUID(), balance = 100.0)
        every { userQueueTokenRepository.findByUserIdAndStatusNot(any(), any()) } returns emptyList()

        // When
        userQueueTokenDomain.validateUserForTokenCreation(user)

        // Then
        verify(exactly = 1) { userQueueTokenRepository.findByUserIdAndStatusNot(user.id, any()) }
    }
}