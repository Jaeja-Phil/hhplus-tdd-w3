package com.example.hhplusweek3.entity.performanceSeatBookInfo

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class PerformanceSeatBookInfoEntityTest {
    private val now = LocalDateTime.now()

    @Test
    fun `toDomain - should raise IllegalArgumentException when id is null`() {
        // given
        val performanceSeatBookInfoEntity = PerformanceSeatBookInfoEntity(
            id = null,
            performanceSeat = mockk(),
            token = "token",
            bookAttemptAt = now,
            bookSuccessAt = now
        )

        // when & then
        assertThrows<IllegalArgumentException> {
            performanceSeatBookInfoEntity.toDomain()
        }
    }
    @Test
    fun `toDomain - should return PerformanceSeatBookInfo`() {
        // given
        val performanceSeatBookInfoEntity = PerformanceSeatBookInfoEntity(
            id = 1L,
            performanceSeat = mockk() {
                every { toDomain() } returns mockk()
            },
            token = "token",
            bookAttemptAt = now,
            bookSuccessAt = now
        )

        // when
        val result = performanceSeatBookInfoEntity.toDomain()

        // then
        assertEquals(1L, result.id)
        assertEquals("token", result.token)
        assertEquals(now, result.bookAttemptAt)
        assertEquals(now, result.bookSuccessAt)
        verify(exactly = 1) { performanceSeatBookInfoEntity.performanceSeat.toDomain() }
    }
}