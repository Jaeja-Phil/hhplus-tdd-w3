package com.example.hhplusweek3.domain.userQueueToken

import com.example.hhplusweek3.domain.User
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class UserQueueTokenTest {
    @Test
    fun `toEntity - should return UserQueueTokenEntity`() {
        // Given
        val userQueueToken = UserQueueToken(
            id = 1L,
            user = User(
                id = UUID.randomUUID(),
                balance = 100.0,
            ),
            token = "token",
            status = UserQueueTokenStatus.IN_QUEUE,
        )

        // When
        val result = userQueueToken.toEntity()

        // Then
        assertEquals(userQueueToken.id, result.id)
        assertEquals(userQueueToken.user.id, result.user.id)
        assertEquals(userQueueToken.user.balance, result.user.balance)
        assertEquals(userQueueToken.token, result.token)
        assertEquals(userQueueToken.status, result.status)
    }
}