package com.example.hhplusweek3.domain.performanceSeat

import com.example.hhplusweek3.domain.concert.ConcertPerformance
import com.example.hhplusweek3.domain.user.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PerformanceSeatTest {
    @Test
    fun `toEntity - should return PerformanceSeatEntity`() {
        // Given
        val concertPerformanceMock = mockk<ConcertPerformance>() {
            every { toEntity() } returns mockk()
        }
        val userMock = mockk<User>() {
            every { toEntity() } returns mockk()

        }
        val performanceSeat = PerformanceSeat(
            id = 1,
            concertPerformance = concertPerformanceMock,
            seatNumber = 1,
            user = userMock,
            booked = false,
            bookAttemptAt = null,
            bookSuccessAt = null,
        )

        // When
        val result = performanceSeat.toEntity()

        // Then
        assertEquals(performanceSeat.id, result.id)
        assertEquals(performanceSeat.seatNumber, result.seatNumber)
        assertEquals(performanceSeat.booked, result.booked)
        verify(exactly = 1) { concertPerformanceMock.toEntity() }
        verify(exactly = 1) { userMock.toEntity() }
    }
}