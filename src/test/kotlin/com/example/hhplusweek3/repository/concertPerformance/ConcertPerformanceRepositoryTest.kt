package com.example.hhplusweek3.repository.concertPerformance

import com.example.hhplusweek3.entity.concert.ConcertEntity
import com.example.hhplusweek3.entity.concertPerformance.ConcertPerformanceEntity
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ConcertPerformanceRepositoryTest {
    private val concertPerformanceJpaRepository = mockk<ConcertPerformanceJpaRepository>()
    private val concertPerformanceRepository = ConcertPerformanceRepository(concertPerformanceJpaRepository)
    private val now = LocalDateTime.now()

    @Test
    fun `getAvailableConcertPerformances - should return available concert performances`() {
        // Given
        val concertPerformances = listOf(
            ConcertPerformanceEntity(
                id = 1,
                concert = ConcertEntity(
                    id = 1,
                    name = "concert1",
                ),
                maxSeats = 100,
                performanceDateTime = now
            )
        )
        every {
            concertPerformanceJpaRepository.findAllAvailableByConcertIdAndPerformanceDateTimeAfter(1, now)
        } returns concertPerformances

        // When
        val result = concertPerformanceRepository.getAvailableConcertPerformances(1, now)

        // Then
        assertEquals(concertPerformances.map { it.toDomain() }, result)
    }
}