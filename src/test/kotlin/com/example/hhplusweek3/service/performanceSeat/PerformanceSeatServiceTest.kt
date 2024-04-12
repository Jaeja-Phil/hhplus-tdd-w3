package com.example.hhplusweek3.service.performanceSeat

import com.example.hhplusweek3.domain.concert.Concert
import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatCreateObject
import com.example.hhplusweek3.domain.performanceSeatBookInfo.PerformanceSeatBookInfo
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
                performanceSeatBookInfo = null
            )
        )

        // when
        val result = performanceSeatService.getAllAvailableSeatNumbersByConcertPerformance(concertPerformance)

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
            performanceSeatBookInfo = null
        )

        // when
        val result = performanceSeatService.getBySeatNumberAndConcertPerformanceId(seatNumber, concertPerformanceId)

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
            performanceSeatBookInfo = null
        )

        // when
        val result = performanceSeatService.createPerformanceSeat(performanceSeatCreateObject)

        // then
        assertEquals(1, result.seatNumber)
    }

    @Test
    fun `update - should return updated performance seat`() {
        // given
        val performanceSeatBookInfoMock = mockk<PerformanceSeatBookInfo>() {
            every { toEntity() } returns mockk()
        }
        val performanceSeat = mockk<PerformanceSeat>() {
            every { id } returns 1
            every { toEntity() } returns mockk()
            every { seatNumber } returns 1
            every { concertPerformance } returns mockk() {
                every { toEntity() } returns mockk()
            }
            every { user } returns null
            every { booked } returns false
            every { performanceSeatBookInfo } returns performanceSeatBookInfoMock
        }

        every {
            performanceSeatRepository.update(any())
        } returns PerformanceSeat(
            id = 1L,
            concertPerformance = mockk(),
            seatNumber = 1,
            user = null,
            booked = false,
            performanceSeatBookInfo = performanceSeatBookInfoMock
        )

        // when
        val result = performanceSeatService.update(performanceSeat)

        // then
        assertEquals(1, result.seatNumber)
    }
}