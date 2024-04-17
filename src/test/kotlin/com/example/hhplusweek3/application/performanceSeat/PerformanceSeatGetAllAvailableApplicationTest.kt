package com.example.hhplusweek3.application.performanceSeat

import com.example.hhplusweek3.domain.concert.ConcertDomain
import com.example.hhplusweek3.domain.concert.ConcertPerformance
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatDomain
import com.example.hhplusweek3.error.NotFoundException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PerformanceSeatGetAllAvailableApplicationTest {
    private val performanceSeatDomain = mockk<PerformanceSeatDomain>()
    private val concertDomain = mockk<ConcertDomain>()
    private val performanceSeatGetAllAvailableApplication = PerformanceSeatGetAllAvailableApplication(
        performanceSeatDomain,
        concertDomain
    )

    @Test
    fun `run - should throw NotFoundException when concertPerformance not found`() {
        // given
        val concertPerformanceId = 1L
        every { concertDomain.getConcertPerformanceById(concertPerformanceId) } returns null

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
        every { concertDomain.getConcertPerformanceById(concertPerformanceId) } returns concertPerformance
        every { performanceSeatDomain.getAllAvailableSeatNumbersByConcertPerformance(concertPerformance) } returns availableSeatNumbers

        // when
        val result = performanceSeatGetAllAvailableApplication.run(concertPerformanceId)

        // then
        assertEquals(availableSeatNumbers, result)
    }
}