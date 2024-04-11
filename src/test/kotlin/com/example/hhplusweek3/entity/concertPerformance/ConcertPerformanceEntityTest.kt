package com.example.hhplusweek3.entity.concertPerformance

import com.example.hhplusweek3.entity.concert.ConcertEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class ConcertPerformanceEntityTest {
    private val now = LocalDateTime.now()

    @Test
    fun `toDomain - should raise error when id is null`() {
        // Given
        val concertPerformanceEntity = ConcertPerformanceEntity(
            id = null,
            concert = ConcertEntity(
                id = 1L,
                name = "Concert",
            ),
            maxSeats = 50,
            performanceDateTime = now
        )

        // When & Then
        assertThrows<IllegalArgumentException> {
            concertPerformanceEntity.toDomain()
        }
    }

    @Test
    fun `toDomain - should return ConcertPerformance`() {
        // Given
        val concertPerformanceEntity = ConcertPerformanceEntity(
            id = 1L,
            concert = ConcertEntity(
                id = 1L,
                name = "Concert",
            ),
            maxSeats = 50,
            performanceDateTime = now
        )

        // When
        val result = concertPerformanceEntity.toDomain()

        // Then
        assertEquals(1L, result.id)
        assertEquals(1L, result.concert.id)
        assertEquals(50, result.maxSeats)
        assertEquals(now, result.performanceDateTime)
    }
}