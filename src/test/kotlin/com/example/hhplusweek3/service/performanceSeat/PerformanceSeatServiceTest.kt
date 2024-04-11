package com.example.hhplusweek3.service.performanceSeat

import com.example.hhplusweek3.domain.concert.Concert
import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat
import com.example.hhplusweek3.repository.performanceSeat.PerformanceSeatRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class PerformanceSeatServiceTest {
    private val performanceSeatRepository = mockk<PerformanceSeatRepository>()
    private val performanceSeatService = PerformanceSeatService(performanceSeatRepository)

    @Test
    fun `getAllAvailableSeatNumbersByConcertPerformance - should return available seat numbers`() {
        // given
        val concert = Concert(id = 1L, name ="Concert 1")
        val concertPerformance = ConcertPerformance(
            id = 1L,
            concert = concert,
            maxSeats = 2,
            performanceDateTime = LocalDateTime.now()
        )
        every {
            performanceSeatRepository.findAllByConcertPerformanceIdAndBooked(1L, true)
        } returns listOf(
            PerformanceSeat(
                id = 1L,
                concertPerformance = mockk(),
                seatNumber = 1,
                user = null,
                booked = true
            )
        )

        // when
        val result = performanceSeatService.getAllAvailableSeatNumbersByConcertPerformance(concertPerformance)

        // then
        assertEquals(listOf(2), result)
    }
}