package com.example.hhplusweek3.application.performanceSeat

import com.example.hhplusweek3.controller.request.PerformanceSeatBookRequest
import com.example.hhplusweek3.domain.concert.ConcertDomain
import com.example.hhplusweek3.domain.concert.ConcertPerformance
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatDomain
import com.example.hhplusweek3.domain.userQueueToken.UserQueueToken
import com.example.hhplusweek3.domain.userQueueToken.UserQueueTokenDomain
import com.example.hhplusweek3.error.BadRequestException
import com.example.hhplusweek3.error.NotFoundException
import com.example.hhplusweek3.repository.performanceSeat.PerformanceSeatRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.redis.core.RedisTemplate

// TODO: fix failing test cases
class PerformanceSeatBookApplicationTest {
    private val concertPerformanceDomain = mockk<ConcertDomain>()
    private val performanceSeatRepository = mockk<PerformanceSeatRepository>()
    private val redisTemplate = mockk<RedisTemplate<String, String>>()
    private val performanceSeatDomain = spyk(PerformanceSeatDomain(performanceSeatRepository, redisTemplate))
    private val userQueueTokenDomain = mockk<UserQueueTokenDomain>()
    private val performanceSeatBookApplication = PerformanceSeatBookApplication(
        concertDomain = concertPerformanceDomain,
        performanceSeatDomain = performanceSeatDomain,
        userQueueTokenDomain = userQueueTokenDomain
    )

    @Test
    fun `run - should throw NotFoundException when concert performance is not found`() {
        // given
        val request = PerformanceSeatBookRequest(
            concertPerformanceId = 1,
            seatNumber = 1
        )
        val userQueueToken = mockk<UserQueueToken>()
        every { concertPerformanceDomain.getConcertPerformanceById(any()) } returns null

        // when
        val thrown = assertThrows<NotFoundException> {
            performanceSeatBookApplication.run(request, userQueueToken)
        }

        // then
        assertEquals("Concert performance is not found.", thrown.message)
    }

    @Test
    fun `run - should throw BadRequestException when seat number is invalid`() {
        // given
        val totalSeats = 10
        val request = PerformanceSeatBookRequest(
            concertPerformanceId = 1,
            seatNumber = totalSeats + 1
        )
        val userQueueToken = mockk<UserQueueToken>()
        val concertPerformance = mockk<ConcertPerformance>() {
            every { maxSeats } returns totalSeats
        }
        every { concertPerformanceDomain.getConcertPerformanceById(any()) } returns concertPerformance

        // when
        val thrown = assertThrows<BadRequestException> {
            performanceSeatBookApplication.run(request, userQueueToken)
        }

        // then
        assertEquals("Invalid seat number.", thrown.message)
    }

    @Test
    fun `run - should throw BadRequestException when tokenReservation is present`() {
        // given
        val seatNumber = 1
        val request = PerformanceSeatBookRequest(
            concertPerformanceId = 1,
            seatNumber = seatNumber
        )
        val userQueueToken = mockk<UserQueueToken>() {
            every { id } returns 1L
        }
        val concertPerformance = mockk<ConcertPerformance>() {
            every { maxSeats } returns seatNumber + 1
        }
        every { concertPerformanceDomain.getConcertPerformanceById(any()) } returns concertPerformance
        every { userQueueTokenDomain.getTokenReservationsByUserQueueTokenId(any()) } returns listOf(mockk())

        // when
        val thrown = assertThrows<BadRequestException> {
            performanceSeatBookApplication.run(request, userQueueToken)
        }

        // then
        assertEquals("User can only book 1 seat with 1 token.", thrown.message)
    }

    @Test
    fun `run - should throw exception when preOccupying performance seat fails`() {
        // given
        val seatNumber = 1
        val request = PerformanceSeatBookRequest(
            concertPerformanceId = 1,
            seatNumber = seatNumber
        )
        val userQueueToken = mockk<UserQueueToken>() {
            every { id } returns 1L
        }
        val concertPerformance = mockk<ConcertPerformance>() {
            every { maxSeats } returns seatNumber + 1
        }
        every { concertPerformanceDomain.getConcertPerformanceById(any()) } returns concertPerformance
        every { userQueueTokenDomain.getTokenReservationsByUserQueueTokenId(any()) } returns emptyList()
        every {
            performanceSeatDomain.preOccupy(
                any(),
                any()
            )
        } throws RuntimeException("fail in preOccupying performance seat")

        // when
        val thrown = assertThrows<RuntimeException> {
            performanceSeatBookApplication.run(request, userQueueToken)
        }

        // then
        assertEquals("fail in preOccupying performance seat", thrown.message)
    }

    @Test
    fun `run - should release redis key and throw exception when get create performance seat fails`() {
        // given
        val seatNumber = 1
        val request = PerformanceSeatBookRequest(
            concertPerformanceId = 1,
            seatNumber = seatNumber
        )
        val userQueueToken = mockk<UserQueueToken>() {
            every { id } returns 1L
        }
        val concertPerformance = mockk<ConcertPerformance>() {
            every { maxSeats } returns seatNumber + 1
        }
        every { concertPerformanceDomain.getConcertPerformanceById(any()) } returns concertPerformance
        every { userQueueTokenDomain.getTokenReservationsByUserQueueTokenId(any()) } returns emptyList()
        every {
            performanceSeatDomain.preOccupy(
                any(),
                any()
            )
        } returns Unit
        every {
            performanceSeatDomain.getBySeatNumberAndConcertPerformanceId(
                any(),
                any()
            )
        } returns null
        every {
            performanceSeatDomain.createPerformanceSeat(
                any()
            )
        } throws RuntimeException("fail in get performance seat")
        every {
            performanceSeatDomain.releasePreOccupied(
                any(),
                any()
            )
        } returns Unit

        // when
        val thrown = assertThrows<RuntimeException> {
            performanceSeatBookApplication.run(request, userQueueToken)
        }

        // then
        assertEquals("fail in get performance seat", thrown.message)
        verify(exactly = 1) {
            performanceSeatDomain.releasePreOccupied(
                concertPerformanceId = request.concertPerformanceId,
                seatNumber = request.seatNumber
            )
        }
        verify(exactly = 1) {
            performanceSeatDomain.preOccupy(
                concertPerformanceId = request.concertPerformanceId,
                seatNumber = request.seatNumber
            )
        }
    }

    @Test
    fun `run - should release redis key and throw exception when create performance seat fails`() {
        // given
        val seatNumber = 1
        val request = PerformanceSeatBookRequest(
            concertPerformanceId = 1,
            seatNumber = seatNumber
        )
        val userQueueToken = mockk<UserQueueToken>() {
            every { id } returns 1L
        }
        val concertPerformance = mockk<ConcertPerformance>() {
            every { maxSeats } returns seatNumber + 1
        }
        every { concertPerformanceDomain.getConcertPerformanceById(any()) } returns concertPerformance
        every { userQueueTokenDomain.getTokenReservationsByUserQueueTokenId(any()) } returns emptyList()
        every {
            performanceSeatDomain.preOccupy(
                any(),
                any()
            )
        } returns Unit
        every {
            performanceSeatDomain.getBySeatNumberAndConcertPerformanceId(
                any(),
                any()
            )
        } returns null
        every {
            performanceSeatDomain.createPerformanceSeat(
                any()
            )
        } throws RuntimeException("fail in create performance seat")
        every {
            performanceSeatDomain.releasePreOccupied(
                any(),
                any()
            )
        } returns Unit

        // when
        val thrown = assertThrows<RuntimeException> {
            performanceSeatBookApplication.run(request, userQueueToken)
        }

        // then
        assertEquals("fail in create performance seat", thrown.message)
        verify(exactly = 1) {
            performanceSeatDomain.releasePreOccupied(
                concertPerformanceId = request.concertPerformanceId,
                seatNumber = request.seatNumber
            )
        }
        verify(exactly = 1) {
            performanceSeatDomain.preOccupy(
                concertPerformanceId = request.concertPerformanceId,
                seatNumber = request.seatNumber
            )
        }
    }

    @Test
    fun `run - should release redis key and throw exception when creating token reservation fails`() {
        // given
        val seatNumber = 1
        val request = PerformanceSeatBookRequest(
            concertPerformanceId = 1,
            seatNumber = seatNumber
        )
        val userQueueToken = mockk<UserQueueToken>() {
            every { id } returns 1L
        }
        val concertPerformance = mockk<ConcertPerformance>() {
            every { maxSeats } returns seatNumber + 1
        }
        val performanceSeat = mockk<PerformanceSeat>()
        every { concertPerformanceDomain.getConcertPerformanceById(any()) } returns concertPerformance
        every { userQueueTokenDomain.getTokenReservationsByUserQueueTokenId(any()) } returns emptyList()
        every {
            performanceSeatDomain.preOccupy(
                any(),
                any()
            )
        } returns Unit
        every {
            performanceSeatDomain.getBySeatNumberAndConcertPerformanceId(
                any(),
                any()
            )
        } returns performanceSeat
        every {
            performanceSeatDomain.createPerformanceSeat(
                any()
            )
        } returns performanceSeat
        every {
            userQueueTokenDomain.createTokenReservation(
                any(),
                any()
            )
        } throws RuntimeException("fail in creating token reservation")
        every {
            performanceSeatDomain.releasePreOccupied(
                any(),
                any()
            )
        } returns Unit

        // when
        val thrown = assertThrows<RuntimeException> {
            performanceSeatBookApplication.run(request, userQueueToken)
        }

        // then
        assertEquals("fail in creating token reservation", thrown.message)
        verify(exactly = 1) {
            performanceSeatDomain.releasePreOccupied(
                concertPerformanceId = request.concertPerformanceId,
                seatNumber = request.seatNumber
            )
        }
        verify(exactly = 1) {
            performanceSeatDomain.preOccupy(
                concertPerformanceId = request.concertPerformanceId,
                seatNumber = request.seatNumber
            )
        }
    }

    @Test
    fun `run - should release redis key and throw exception when updating performance seat fails`() {
        // given
        val seatNumber = 1
        val request = PerformanceSeatBookRequest(
            concertPerformanceId = 1,
            seatNumber = seatNumber
        )
        val userQueueToken = mockk<UserQueueToken>() {
            every { id } returns 1L
        }
        val concertPerformance = mockk<ConcertPerformance>() {
            every { maxSeats } returns seatNumber + 1
        }
        val performanceSeat = mockk<PerformanceSeat>() {
            every { book(any()) } returns this
        }
        every { concertPerformanceDomain.getConcertPerformanceById(any()) } returns concertPerformance
        every { userQueueTokenDomain.getTokenReservationsByUserQueueTokenId(any()) } returns emptyList()
        every {
            performanceSeatDomain.preOccupy(
                any(),
                any()
            )
        } returns Unit
        every {
            performanceSeatDomain.getBySeatNumberAndConcertPerformanceId(
                any(),
                any()
            )
        } returns performanceSeat
        every {
            performanceSeatDomain.createPerformanceSeat(
                any()
            )
        } returns performanceSeat
        every {
            userQueueTokenDomain.createTokenReservation(
                any(),
                any()
            )
        } returns mockk()
        every {
            performanceSeatDomain.update(
                any()
            )
        } throws RuntimeException("fail in updating performance seat")
        every {
            performanceSeatDomain.releasePreOccupied(
                any(),
                any()
            )
        } returns Unit

        // when
        val thrown = assertThrows<RuntimeException> {
            performanceSeatBookApplication.run(request, userQueueToken)
        }

        // then
        assertEquals("fail in updating performance seat", thrown.message)
        verify(exactly = 1) {
            performanceSeatDomain.releasePreOccupied(
                concertPerformanceId = request.concertPerformanceId,
                seatNumber = request.seatNumber
            )
        }
        verify(exactly = 1) {
            performanceSeatDomain.preOccupy(
                concertPerformanceId = request.concertPerformanceId,
                seatNumber = request.seatNumber
            )
        }
    }

    @Test
    fun `run - should return booked performance seat`() {
        // given
        val numberOfSeats = 1
        val request = PerformanceSeatBookRequest(
            concertPerformanceId = 1,
            seatNumber = numberOfSeats
        )
        val userQueueToken = mockk<UserQueueToken>() {
            every { id } returns 1L
        }
        val concertPerformanceMock = mockk<ConcertPerformance>() {
            every { id } returns 1L
            every { maxSeats } returns numberOfSeats + 1
        }
        val performanceSeat = mockk<PerformanceSeat>() {
            every { id } returns 1L
            every { concertPerformance } returns concertPerformanceMock
            every { seatNumber } returns numberOfSeats
            every { booked } returns false
            every { book(any()) } returns this
        }
        every { concertPerformanceDomain.getConcertPerformanceById(any()) } returns concertPerformanceMock
        every { userQueueTokenDomain.getTokenReservationsByUserQueueTokenId(any()) } returns emptyList()
        every {
            performanceSeatDomain.preOccupy(
                any(),
                any()
            )
        } returns Unit
        every {
            performanceSeatDomain.getBySeatNumberAndConcertPerformanceId(
                any(),
                any()
            )
        } returns performanceSeat
        every {
            userQueueTokenDomain.createTokenReservation(
                any(),
                any()
            )
        } returns mockk()
        every {
            performanceSeatDomain.update(
                any()
            )
        } returns performanceSeat

        // when
        val result = performanceSeatBookApplication.run(request, userQueueToken)

        // then
        verify(exactly = 0) {
            performanceSeatDomain.releasePreOccupied(
                concertPerformanceId = request.concertPerformanceId,
                seatNumber = request.seatNumber
            )
        }
    }
}