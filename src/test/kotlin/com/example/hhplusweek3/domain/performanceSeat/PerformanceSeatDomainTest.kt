package com.example.hhplusweek3.domain.performanceSeat

import com.example.hhplusweek3.domain.concert.Concert
import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatCreateObject
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatDomain
import com.example.hhplusweek3.repository.performanceSeat.PerformanceSeatRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class PerformanceSeatDomainTest {
    private val performanceSeatRepository = mockk<PerformanceSeatRepository>()
    private val performanceSeatDomain = PerformanceSeatDomain(performanceSeatRepository)

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
            performanceSeatRepository.findAllByConcertPerformanceIdAndBookedAndUserId(
                concertPerformanceId = 1L,
                booked = true,
                userId = null)
        } returns listOf(
            PerformanceSeat(
                id = 1L,
                concertPerformance = mockk(),
                seatNumber = 1,
                user = null,
                booked = true,
                bookAttemptAt = null,
                bookSuccessAt = null
            )
        )

        // when
        val result = performanceSeatDomain.getAllAvailableSeatNumbersByConcertPerformance(concertPerformance)

        // then
        assertEquals(listOf(2), result)
    }

    @Test
    fun `getBySeatNumberAndConcertPerformanceId - should return performance seat by seat number and concert performance id`() {
        // given
        val concertPerformanceId = 1L
        val seatNumber = 1
        every {
            performanceSeatRepository.findBySeatNumberAndConcertPerformanceId(
                seatNumber = seatNumber,
                concertPerformanceId = concertPerformanceId
            )
        } returns PerformanceSeat(
            id = 1L,
            concertPerformance = mockk(),
            seatNumber = seatNumber,
            user = null,
            booked = false,
            bookAttemptAt = null,
            bookSuccessAt = null
        )

        // when
        val result = performanceSeatDomain.getBySeatNumberAndConcertPerformanceId(seatNumber, concertPerformanceId)

        // then
        assertEquals(seatNumber, result?.seatNumber)
    }

    @Test
    fun `createPerformanceSeat - should return created performance seat`() {
        // given
        val performanceSeatCreateObject = PerformanceSeatCreateObject(
            concertPerformance = mockk(),
            seatNumber = 1
        )
        every {
            performanceSeatRepository.save(performanceSeatCreateObject)
        } returns PerformanceSeat(
            id = 1L,
            concertPerformance = mockk(),
            seatNumber = 1,
            user = null,
            booked = false,
            bookAttemptAt = null,
            bookSuccessAt = null
        )

        // when
        val result = performanceSeatDomain.createPerformanceSeat(performanceSeatCreateObject)

        // then
        assertEquals(1, result.seatNumber)
    }

    @Test
    fun `update - should return updated performance seat`() {
        // given
        val performanceSeat = mockk<PerformanceSeat>() {
            every { id } returns 1
            every { toEntity() } returns mockk()
            every { seatNumber } returns 1
            every { concertPerformance } returns mockk() {
                every { toEntity() } returns mockk()
            }
            every { user } returns null
            every { booked } returns false
            every { bookAttemptAt } returns null
            every { bookSuccessAt } returns null
        }

        every {
            performanceSeatRepository.update(any())
        } returns PerformanceSeat(
            id = 1L,
            concertPerformance = mockk(),
            seatNumber = 1,
            user = null,
            booked = false,
            bookAttemptAt = null,
            bookSuccessAt = null
        )

        // when
        val result = performanceSeatDomain.update(performanceSeat)

        // then
        assertEquals(1, result.seatNumber)
    }
}