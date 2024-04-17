package com.example.hhplusweek3.entity.performanceSeat

import com.example.hhplusweek3.entity.concert.ConcertPerformanceEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PerformanceSeatEntityTest {
    @Test
    fun `toDomain - should raise error when id is null`() {
        // Given
        val performanceSeatEntity = PerformanceSeatEntity(
            id = null,
            concertPerformance = mockk(),
            seatNumber = 1,
            user = null,
            booked = false,
            bookAttemptAt = null,
            bookSuccessAt = null
        )

        // When & Then
        assertThrows<IllegalArgumentException> {
            performanceSeatEntity.toDomain()
        }
    }

    @Test
    fun `toDomain - should return PerformanceSeat`() {
        // Given
        val concertPerformance = mockk<ConcertPerformanceEntity>() {
            every { toDomain() } returns mockk()
        }
        val performanceSeatEntity = PerformanceSeatEntity(
            id = 1,
            concertPerformance = concertPerformance,
            seatNumber = 1,
            user = null,
            booked = false,
            bookAttemptAt = null,
            bookSuccessAt = null
        )

        // When
        val result = performanceSeatEntity.toDomain()

        // Then
        assert(result.id == performanceSeatEntity.id)
        assert(result.seatNumber == performanceSeatEntity.seatNumber)
        assert(result.booked == performanceSeatEntity.booked)
        verify(exactly = 1) { concertPerformance.toDomain() }
    }
}