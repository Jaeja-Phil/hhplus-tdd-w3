package com.example.hhplusweek3.application.concertPerformance

import com.example.hhplusweek3.application.concert.ConcertPerformanceGetAllAvailableApplication
import com.example.hhplusweek3.controller.response.ConcertPerformanceResponse
import com.example.hhplusweek3.domain.concert.ConcertDomain
import com.example.hhplusweek3.domain.concert.ConcertPerformance
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ConcertPerformanceGetAllAvailableApplicationTest {
    private val concertDomain = mockk<ConcertDomain>()
    private val concertPerformanceGetAllAvailableApplication = ConcertPerformanceGetAllAvailableApplication(concertDomain)
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
            concertDomain.getAvailableConcertPerformances(100L)
        } returns concertPerformances

        // When
        val SUT = concertPerformanceGetAllAvailableApplication.run(100L)

        // Then
        assertEquals(concertPerformances.map { ConcertPerformanceResponse.from(it) }, SUT)
    }
}