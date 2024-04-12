package com.example.hhplusweek3.entity.performanceSeatBookInfo

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class PerformanceSeatBookInfoEntityTest {
    private val now = LocalDateTime.now()
    @Test
    fun `toDomain - should return PerformanceSeatBookInfo`() {
        // given
        val performanceSeatBookInfoEntity = PerformanceSeatBookInfoEntity(
            performanceSeat = mockk() {
                every { toDomain() } returns mockk() {
                    every { toDomain(any()) } returns mockk()
                }
            },
            token = "token",
            bookAttemptAt = now,
            bookSuccessAt = now,
            id = 1
        )

        // when
        val result = performanceSeatBookInfoEntity.toDomain()

        // then
        assertEquals("token", result.token)
        assertEquals(now, result.bookAttemptAt)
        assertEquals(now, result.bookSuccessAt)
        verify(exactly = 1) { performanceSeatBookInfoEntity.performanceSeat?.toDomain(any()) }
    }
}