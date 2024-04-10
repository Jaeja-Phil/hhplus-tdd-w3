package com.example.hhplusweek3.repository.userQueueToken

import com.example.hhplusweek3.domain.userQueueToken.UserQueueTokenCreateObject
import com.example.hhplusweek3.entity.user.UserEntity
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenEntity
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.*

class UserQueueTokenRepositoryTest {
    private val userQueueTokenJpaRepository = mockk<UserQueueTokenJpaRepository>()
    private val userQueueTokenRepository = UserQueueTokenRepository(userQueueTokenJpaRepository)

    @Test
    fun `save - should save with passed UserQueueTokenCreateObject`() {
        // Given
        val userEntity = UserEntity(id = UUID.randomUUID(), balance = 100.0)
        val userQueueTokenCreateObject = UserQueueTokenCreateObject(
            user = userEntity,
            token = "token"
        )
        val userQueueTokenEntity = UserQueueTokenEntity(
            user = userEntity,
            token = userQueueTokenCreateObject.token
        )
        val savedUserQueueTokenEntity = userQueueTokenEntity.copy(id = 1L)
        every { userQueueTokenJpaRepository.save(userQueueTokenEntity) } returns savedUserQueueTokenEntity

        // When
        val result = userQueueTokenRepository.save(userQueueTokenCreateObject)

        // Then
        assertEquals(savedUserQueueTokenEntity.id, result.id)
        assertEquals(savedUserQueueTokenEntity.user.toDomain(), result.user)
        assertEquals(savedUserQueueTokenEntity.token, result.token)
        assertEquals(savedUserQueueTokenEntity.status, result.status)
        assertEquals(UserQueueTokenStatus.IN_QUEUE, result.status)
        verify { userQueueTokenJpaRepository.save(userQueueTokenEntity) }
    }

    @Test
    fun `findByToken - should return null if not found`() {
        // Given
        val token = "token"
        every { userQueueTokenJpaRepository.findByToken(token) } returns null

        // When
        val result = userQueueTokenRepository.findByToken(token)

        // Then
        assertNull(result)
        verify { userQueueTokenJpaRepository.findByToken(token) }
    }

    @Test
    fun `findByToken - should return UserQueueToken if found`() {
        // Given
        val userEntity = UserEntity(id = UUID.randomUUID(), balance = 100.0)
        val userQueueTokenEntity = UserQueueTokenEntity(
            id = 1L,
            user = userEntity,
            token = "token"
        )
        every { userQueueTokenJpaRepository.findByToken(userQueueTokenEntity.token) } returns userQueueTokenEntity

        // When
        val result = userQueueTokenRepository.findByToken(userQueueTokenEntity.token)

        // Then
        assertEquals(userQueueTokenEntity.id, result?.id)
        assertEquals(userQueueTokenEntity.user.toDomain(), result?.user)
        assertEquals(userQueueTokenEntity.token, result?.token)
        assertEquals(userQueueTokenEntity.status, result?.status)
    }
}