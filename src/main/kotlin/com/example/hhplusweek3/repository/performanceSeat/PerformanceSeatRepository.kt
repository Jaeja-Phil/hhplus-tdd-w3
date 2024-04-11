package com.example.hhplusweek3.repository.performanceSeat

import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class PerformanceSeatRepository(
    private val performanceSeatJpaRepository: PerformanceSeatJpaRepository
) {
    fun findAllByConcertPerformanceIdAndBookedAndUserId(
        concertPerformanceId: Long,
        booked: Boolean,
        userId: UUID?
    ): List<PerformanceSeat> {
        return performanceSeatJpaRepository
            .findAllByConcertPerformanceIdAndBookedAndUserId(
                concertPerformanceId = concertPerformanceId,
                booked = booked,
                userId = userId
            )
            .map { it.toDomain() }
    }
}