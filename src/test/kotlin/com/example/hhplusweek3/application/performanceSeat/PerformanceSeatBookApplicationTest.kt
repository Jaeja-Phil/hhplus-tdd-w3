package com.example.hhplusweek3.application.performanceSeat

import com.example.hhplusweek3.controller.request.PerformanceSeatBookRequest
import com.example.hhplusweek3.controller.response.PerformanceSeatResponse
import com.example.hhplusweek3.domain.concert.Concert
import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat
import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.domain.userQueueToken.UserQueueToken
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenStatus
import com.example.hhplusweek3.error.BadRequestException
import com.example.hhplusweek3.error.NotFoundException
import com.example.hhplusweek3.service.concertPerformance.ConcertPerformanceService
import com.example.hhplusweek3.service.performanceSeat.PerformanceSeatService
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

class PerformanceSeatBookApplicationTest {
    private val concertPerformanceService: ConcertPerformanceService = mockk()
    private val performanceSeatService = spyk(mockk<PerformanceSeatService>())
    private val performanceSeatBookApplication = PerformanceSeatBookApplication(
        concertPerformanceService = concertPerformanceService,
        performanceSeatService = performanceSeatService
    )

    @Test
    fun `run - should throw NotFoundException when concert performance is not found`() {
        // given
        val request = PerformanceSeatBookRequest(
            concertPerformanceId = 1,
            seatNumber = 1
        )
        val userQueueToken = mockk<UserQueueToken>()
        every { concertPerformanceService.getById(any()) } returns null

        // when
        val thrown = assertThrows<NotFoundException> {
            performanceSeatBookApplication.run(request, userQueueToken)
        }

        // then
        assertEquals("Concert performance is not found.", thrown.message)
    }

    @Test
    fun `run - should throw BadRequestException when performance seat is not available`() {
        // given
        val request = PerformanceSeatBookRequest(
            concertPerformanceId = 1,
            seatNumber = 1
        )
        val userQueueToken = mockk<UserQueueToken>()
        val concertPerformance = mockk<ConcertPerformance>()
        every { concertPerformanceService.getById(any()) } returns concertPerformance
        every { performanceSeatService.getBySeatNumberAndConcertPerformanceId(any(), any()) } returns mockk {
            every { isAvailable() } returns false
        }

        // when
        val thrown = assertThrows<BadRequestException> {
            performanceSeatBookApplication.run(request, userQueueToken)
        }

        // then
        assertEquals("Performance seat is not available.", thrown.message)
    }

    @Test
    fun `run - should create performance seat when performance seat is not found`() {
        // given
        val request = PerformanceSeatBookRequest(concertPerformanceId = 1, seatNumber = 1)
        val user = User(id = UUID.randomUUID(), balance = 0.0)
        val userQueueToken = UserQueueToken(
            id = 1L,
            user = user,
            token = UUID.randomUUID().toString(),
            status = UserQueueTokenStatus.IN_PROGRESS
        )
        val concert = Concert(id = 1, name = "concert")
        val concertPerformanceToReturn = ConcertPerformance(
            id = 1,
            maxSeats = 10,
            concert = concert,
            performanceDateTime = LocalDateTime.now()
        )
        every { concertPerformanceService.getById(any()) } returns concertPerformanceToReturn
        every { performanceSeatService.getBySeatNumberAndConcertPerformanceId(any(), any()) } returns null
        val performanceSeat = mockk<PerformanceSeat>() {
            every { book(any()) } returns this
            every { concertPerformance } returns concertPerformanceToReturn
            every { seatNumber } returns 1
            every { booked } returns false
        }
        every { performanceSeatService.createPerformanceSeat(any()) } returns performanceSeat
        every { performanceSeatService.update(any()) } returns performanceSeat

        // when
        val result = performanceSeatBookApplication.run(request, userQueueToken)

        // then
        verify(exactly = 1) { performanceSeatService.createPerformanceSeat(any()) }
        verify(exactly = 1) { performanceSeatService.update(any()) }
        assertEquals(PerformanceSeatResponse.from(performanceSeat), result)
    }

    @Test
    fun `run - should not create performance seat when performance seat is found`() {
        // given
        val request = PerformanceSeatBookRequest(concertPerformanceId = 1, seatNumber = 1)
        val user = User(id = UUID.randomUUID(), balance = 0.0)
        val userQueueToken = UserQueueToken(
            id = 1L,
            user = user,
            token = UUID.randomUUID().toString(),
            status = UserQueueTokenStatus.IN_PROGRESS
        )
        val concert = Concert(id = 1, name = "concert")
        val concertPerformanceToReturn = ConcertPerformance(
            id = 1,
            maxSeats = 10,
            concert = concert,
            performanceDateTime = LocalDateTime.now()
        )
        every { concertPerformanceService.getById(any()) } returns concertPerformanceToReturn
        val performanceSeat = mockk<PerformanceSeat>() {
            every { isAvailable() } returns true
            every { book(any()) } returns this
            every { concertPerformance } returns concertPerformanceToReturn
            every { seatNumber } returns 1
            every { booked } returns false
        }
        every { performanceSeatService.getBySeatNumberAndConcertPerformanceId(any(), any()) } returns performanceSeat
        every { performanceSeatService.update(any()) } returns performanceSeat

        // when
        val result = performanceSeatBookApplication.run(request, userQueueToken)

        // then
        verify(exactly = 0) { performanceSeatService.createPerformanceSeat(any()) }
        verify(exactly = 1) { performanceSeatService.update(any()) }
        assertEquals(PerformanceSeatResponse.from(performanceSeat), result)
    }
}