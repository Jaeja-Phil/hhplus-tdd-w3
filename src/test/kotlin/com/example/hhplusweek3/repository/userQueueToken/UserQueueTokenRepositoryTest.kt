package com.example.hhplusweek3.repository.userQueueToken

import com.example.hhplusweek3.domain.userQueueToken.UserQueueTokenCreateObject
import com.example.hhplusweek3.entity.user.UserEntity
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenEntity
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenEntityFactory
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenStatus
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class UserQueueTokenRepositoryTest {
    private val userQueueTokenJpaRepository = mockk<UserQueueTokenJpaRepository>()
    private val userQueueTokenRepository = UserQueueTokenRepository(userQueueTokenJpaRepository)
    private val userQueueTokenEntityFactory = mockk<UserQueueTokenEntityFactory>()

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
        every { userQueueTokenEntityFactory.fromCreateObject(userQueueTokenCreateObject) } returns userQueueTokenEntity
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
    }
}