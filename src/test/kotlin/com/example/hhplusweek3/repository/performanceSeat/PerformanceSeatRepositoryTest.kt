package com.example.hhplusweek3.repository.performanceSeat

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
            user = null
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
}