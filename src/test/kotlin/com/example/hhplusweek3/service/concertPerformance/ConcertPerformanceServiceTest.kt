package com.example.hhplusweek3.service.concertPerformance

import com.example.hhplusweek3.domain.concert.Concert
import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
import com.example.hhplusweek3.repository.concertPerformance.ConcertPerformanceRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ConcertPerformanceServiceTest {
    private val concertPerformanceRepository = mockk<ConcertPerformanceRepository>()
    private val concertPerformanceService = ConcertPerformanceService(concertPerformanceRepository)
    private val now = LocalDateTime.now()
    @Test
    fun `getAvailableConcertPerformances - should return available concert performances`() {
        // Given
        val concertPerformances = listOf(
            ConcertPerformance(
                id = 1L,
                concert = Concert(
                    id = 100L,
                    name = "concert1",
                ),
                maxSeats = 100,
                performanceDateTime = now
            )
        )
        every {
            concertPerformanceRepository.getAvailableConcertPerformances(100L, any())
        } returns concertPerformances

        // When
        val SUT = concertPerformanceService.getAvailableConcertPerformances(100L)

        // Then
        assertEquals(concertPerformances, SUT)
    }

    @Test
    fun `getConcertById - should return null when concert not found`() {
        // Given
        every { concertPerformanceRepository.findById(1L) } returns null

        // When
        val result = concertPerformanceService.getConcertById(1L)

        // Then
        assertEquals(null, result)
    }

    @Test
    fun `getConcertById - should return concert when concert found`() {
        // Given
        val concertPerformance = ConcertPerformance(
            id = 1L,
            concert = mockk(),
            maxSeats = 100,
            performanceDateTime = now
        )
        every { concertPerformanceRepository.findById(1L) } returns concertPerformance

        // When
        val result = concertPerformanceService.getConcertById(1L)

        // Then
        assertEquals(concertPerformance, result)
    }
}