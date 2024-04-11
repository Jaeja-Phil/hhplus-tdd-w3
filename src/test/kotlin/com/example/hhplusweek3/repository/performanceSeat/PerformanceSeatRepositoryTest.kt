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
    fun `findAllByConcertPerformanceIdAndBooked - should return all performance seats by concert performance id and booked`() {
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
            performanceSeatJpaRepository.findAllByConcertPerformanceIdAndBooked(concertPerformanceId, booked)
        } returns listOf(performanceSeatEntity)

        // when
        val SUT = performanceSeatRepository.findAllByConcertPerformanceIdAndBooked(concertPerformanceId, booked)

        // then
        assertEquals(1, SUT.size)
        assertEquals(performanceSeatEntity.toDomain(), SUT[0])
    }
}