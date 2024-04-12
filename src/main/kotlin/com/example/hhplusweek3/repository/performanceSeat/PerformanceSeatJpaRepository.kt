package com.example.hhplusweek3.repository.performanceSeat

import com.example.hhplusweek3.entity.performanceSeat.PerformanceSeatEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PerformanceSeatJpaRepository : JpaRepository<PerformanceSeatEntity, Long> {
    fun findAllByConcertPerformanceIdAndBookedAndUserId(
        concertPerformanceId: Long,
        booked: Boolean,
        userId: UUID?
    ): List<PerformanceSeatEntity>

    fun findBySeatNumberAndConcertPerformanceId(seatNumber: Int, concertPerformanceId: Long): PerformanceSeatEntity?
}
