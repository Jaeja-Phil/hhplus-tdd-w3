package com.example.hhplusweek3.entity.userQueueToken

import com.example.hhplusweek3.domain.User
import com.example.hhplusweek3.entity.user.UserEntity
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class UserQueueTokenEntityTest {
    @Test
    fun `toDomain - should raise IllegalStateException when id is null`() {
        // Given
        val userQueueTokenEntity = UserQueueTokenEntity(
            id = null,
            user = UserEntity(
                id = UUID.randomUUID(),
                balance = 100.0,
            ),
            token = "token",
            status = UserQueueTokenStatus.IN_QUEUE,
        )

        // When & Then
        assertThrows<IllegalArgumentException> {
            userQueueTokenEntity.toDomain()
        }
    }

    @Test
    fun `toDomain - should return UserQueueToken`() {
        // Given
        val mockUserEntity = mockk<UserEntity>()
        every { mockUserEntity.toDomain() } returns mockk<User>()
        val userQueueTokenEntity = UserQueueTokenEntity(
            id = 1L,
            user = mockUserEntity,
            token = "token",
            status = UserQueueTokenStatus.IN_QUEUE,
        )

        // When
        val result = userQueueTokenEntity.toDomain()

        // Then
        assertEquals(userQueueTokenEntity.id, result.id)
        assertEquals(userQueueTokenEntity.user.toDomain(), result.user)
        assertEquals(userQueueTokenEntity.token, result.token)
        assertEquals(userQueueTokenEntity.status, result.status)
    }
}