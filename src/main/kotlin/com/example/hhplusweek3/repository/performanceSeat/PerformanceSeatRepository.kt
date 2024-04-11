package com.example.hhplusweek3.repository.performanceSeat

import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat
import org.springframework.stereotype.Repository

@Repository
class PerformanceSeatRepository(
    private val performanceSeatJpaRepository: PerformanceSeatJpaRepository
) {
    fun findAllByConcertPerformanceIdAndBooked(concertPerformanceId: Long, booked: Boolean): List<PerformanceSeat> {
        return performanceSeatJpaRepository
            .findAllByConcertPerformanceIdAndBooked(concertPerformanceId, booked)
            .map { it.toDomain() }
    }
}