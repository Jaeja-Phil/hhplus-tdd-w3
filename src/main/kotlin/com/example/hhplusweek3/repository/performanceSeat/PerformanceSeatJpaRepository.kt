package com.example.hhplusweek3.repository.performanceSeat

import com.example.hhplusweek3.entity.performanceSeat.PerformanceSeatEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PerformanceSeatJpaRepository : JpaRepository<PerformanceSeatEntity, Long> {
    fun findAllByConcertPerformanceIdAndBooked(concertPerformanceId: Long, booked: Boolean): List<PerformanceSeatEntity>
}
