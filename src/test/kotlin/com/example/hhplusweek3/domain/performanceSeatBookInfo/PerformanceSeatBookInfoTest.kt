package com.example.hhplusweek3.domain.performanceSeatBookInfo

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class PerformanceSeatBookInfoTest {
    private val now = LocalDateTime.now()
    @Test
    fun `toEntity - should convert PerformanceSeatBookInfo to PerformanceSeatBookInfoEntity`() {
        // Given
        val performanceSeatBookInfo = PerformanceSeatBookInfo(
            performanceSeat = mockk() {
                every { toEntity() } returns mockk()
            },
            token = "token",
            bookAttemptAt = now,
            bookSuccessAt = now
        )

        // When
        val result = performanceSeatBookInfo.toEntity()

        // Then
        assertEquals("token", result.token)
        assertEquals(now, result.bookAttemptAt)
        assertEquals(now, result.bookSuccessAt)
        verify(exactly = 1) { performanceSeatBookInfo.performanceSeat?.toEntity() }
    }
}