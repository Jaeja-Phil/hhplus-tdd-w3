package com.example.hhplusweek3.repository.performanceSeat

import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatCreateObject
import com.example.hhplusweek3.entity.performanceSeat.PerformanceSeatEntity
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PerformanceSeatRepositoryTest {
    private val performanceSeatJpaRepository = mockk<PerformanceSeatJpaRepository>()
    private val performanceSeatRepository = PerformanceSeatRepository(performanceSeatJpaRepository)

    @Test
    fun `findAllByConcertPerformanceIdAndBookedAndUserId - should return all performance seats by concertPerformanceId, booked and userId`() {
        // given
        val concertPerformanceId = 1L
        val booked = false
        val performanceSeatEntity = PerformanceSeatEntity(
            id = 1L,
            concertPerformance = mockk() {
                every { toDomain() } returns mockk()
            },
            booked = booked,
            seatNumber = 1,
            user = null,
            performanceSeatBookInfo = null
        )
        every {
            performanceSeatJpaRepository.findAllByConcertPerformanceIdAndBookedAndUserId(
                concertPerformanceId = concertPerformanceId,
                booked = false,
                userId = null
            )
        } returns listOf(performanceSeatEntity)

        // when
        val SUT = performanceSeatRepository
            .findAllByConcertPerformanceIdAndBookedAndUserId(
                concertPerformanceId = concertPerformanceId,
                booked = booked,
                userId = null
            )

        // then
        assertEquals(1, SUT.size)
        assertEquals(performanceSeatEntity.toDomain(), SUT[0])
    }

    @Test
    fun `findBySeatNumberAndConcertPerformanceId - should return performance seat by seatNumber and concertPerformanceId`() {
        // given
        val seatNumber = 1
        val concertPerformanceId = 1L
        val performanceSeatEntity = PerformanceSeatEntity(
            id = 1L,
            concertPerformance = mockk() {
                every { toDomain() } returns mockk()
            },
            booked = false,
            seatNumber = seatNumber,
            user = null,
            performanceSeatBookInfo = null
        )
        every {
            performanceSeatJpaRepository.findBySeatNumberAndConcertPerformanceId(
                seatNumber = seatNumber,
                concertPerformanceId = concertPerformanceId
            )
        } returns performanceSeatEntity

        // when
        val SUT = performanceSeatRepository
            .findBySeatNumberAndConcertPerformanceId(
                seatNumber = seatNumber,
                concertPerformanceId = concertPerformanceId
            )

        // then
        assertEquals(performanceSeatEntity.toDomain(), SUT)
    }

    @Test
    fun `save - should save performance seat`() {
        // given
        val performanceSeatCreateObject = mockk<PerformanceSeatCreateObject>() {
            every { concertPerformance } returns mockk() {
                every { toEntity() } returns mockk()
            }
            every { seatNumber } returns 1
        }
        val performanceSeatEntity = PerformanceSeatEntity(
            id = 1L,
            concertPerformance = mockk() {
                every { toDomain() } returns mockk()
            },
            booked = false,
            seatNumber = 1,
            user = null,
            performanceSeatBookInfo = null
        )
        every { performanceSeatJpaRepository.save(any()) } returns performanceSeatEntity

        // when
        val SUT = performanceSeatRepository.save(performanceSeatCreateObject)

        // then
        assertEquals(performanceSeatEntity.toDomain(), SUT)
    }

    @Test
    fun `update - should update performance seat`() {
        // given
        val performanceSeatEntity = PerformanceSeatEntity(
            id = 1L,
            concertPerformance = mockk() {
                every { toDomain() } returns mockk()
            },
            booked = false,
            seatNumber = 1,
            user = null,
            performanceSeatBookInfo = null
        )
        every { performanceSeatJpaRepository.save(any()) } returns performanceSeatEntity

        // when
        val SUT = performanceSeatRepository.update(performanceSeatEntity)

        // then
        assertEquals(performanceSeatEntity.toDomain(), SUT)
    }
}