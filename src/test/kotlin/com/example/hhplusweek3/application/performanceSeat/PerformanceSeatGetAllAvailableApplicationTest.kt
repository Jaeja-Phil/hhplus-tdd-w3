package com.example.hhplusweek3.application.performanceSeat

import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
import com.example.hhplusweek3.error.NotFoundException
import com.example.hhplusweek3.service.concertPerformance.ConcertPerformanceService
import com.example.hhplusweek3.service.performanceSeat.PerformanceSeatService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PerformanceSeatGetAllAvailableApplicationTest {
    private val performanceSeatService = mockk<PerformanceSeatService>()
    private val concertPerformanceService = mockk<ConcertPerformanceService>()
    private val performanceSeatGetAllAvailableApplication = PerformanceSeatGetAllAvailableApplication(
        performanceSeatService,
        concertPerformanceService
    )

    @Test
    fun `run - should throw NotFoundException when concertPerformance not found`() {
        // given
        val concertPerformanceId = 1L
        every { concertPerformanceService.getConcertById(concertPerformanceId) } returns null

        // when
        val error = assertThrows<NotFoundException> {
            performanceSeatGetAllAvailableApplication.run(concertPerformanceId)
        }

        // then
        assertEquals("ConcertPerformance not found.", error.message)
    }

    @Test
    fun `run - should return all available seat numbers`() {
        // given
        val concertPerformanceId = 1L
        val concertPerformance = mockk<ConcertPerformance>()
        val availableSeatNumbers = listOf(1, 2, 3)
        every { concertPerformanceService.getConcertById(concertPerformanceId) } returns concertPerformance
        every { performanceSeatService.getAllAvailableSeatNumbersByConcertPerformance(concertPerformance) } returns availableSeatNumbers

        // when
        val result = performanceSeatGetAllAvailableApplication.run(concertPerformanceId)

        // then
        assertEquals(availableSeatNumbers, result)
    }
}