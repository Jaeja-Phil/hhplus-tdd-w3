package com.example.hhplusweek3.domain.concert

import com.example.hhplusweek3.domain.concert.Concert
import com.example.hhplusweek3.domain.concert.ConcertPerformance
import com.example.hhplusweek3.entity.concert.ConcertEntity
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ConcertPerformanceTest {
    private val now = LocalDateTime.now()
    @Test
    fun `toEntity - should return ConcertPerformanceEntity`() {
        // Given
        val concertMock = mockk<Concert>() {
            every { toEntity() } answers {
                mockk<ConcertEntity>() {
                    every { id } returns 100L
                }
            }
        }
        val concertPerformance = ConcertPerformance(
            id = 1,
            concert = concertMock,
            maxSeats = 100,
            performanceDateTime = now
        )

        // When
        val result = concertPerformance.toEntity()

        // Then
        assertEquals(1, result.id)
        assertEquals(100, result.concert.id)
        assertEquals(100, result.maxSeats)
        assertEquals(now, result.performanceDateTime)
    }
}