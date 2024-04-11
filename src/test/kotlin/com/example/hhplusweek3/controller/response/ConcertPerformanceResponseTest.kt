package com.example.hhplusweek3.controller.response

import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ConcertPerformanceResponseTest {
    @Test
    fun `from - success`() {
        val now = LocalDateTime.now()
        // given
        val concertPerformance = ConcertPerformance(
            id = 1,
            concert = mockk() {
                every { id } returns 100L
            },
            maxSeats = 100,
            performanceDateTime = now
        )

        // when
        val result = ConcertPerformanceResponse.from(concertPerformance)

        // then
        assertEquals(ConcertPerformanceResponse(
            id = 1,
            concertId = 100L,
            maxSeats = 100,
            performanceDateTime = now
        ), result)
    }
}