package com.example.hhplusweek3.application.concertPerformance

import com.example.hhplusweek3.controller.response.ConcertPerformanceResponse
import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
import com.example.hhplusweek3.service.concertPerformance.ConcertPerformanceService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ConcertPerformanceGetAllAvailableApplicationTest {
    private val concertPerformanceService = mockk<ConcertPerformanceService>()
    private val concertPerformanceGetAllAvailableApplication = ConcertPerformanceGetAllAvailableApplication(concertPerformanceService)
    private val now = LocalDateTime.now()

    @Test
    fun `run - should return available concert performances`() {
        // Given
        val concertPerformances = listOf(
            ConcertPerformance(
                id = 1L,
                concert = mockk() { every { id } returns 100L },
                maxSeats = 100,
                performanceDateTime = now
            )
        )
        every {
            concertPerformanceService.getAvailableConcertPerformances(100L)
        } returns concertPerformances

        // When
        val SUT = concertPerformanceGetAllAvailableApplication.run(100L)

        // Then
        assertEquals(concertPerformances.map { ConcertPerformanceResponse.from(it) }, SUT)
    }
}