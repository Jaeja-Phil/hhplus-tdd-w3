package com.example.hhplusweek3.domain.concertPerformance

import com.example.hhplusweek3.domain.concert.Concert
import com.example.hhplusweek3.entity.concertPerformance.ConcertPerformanceEntity
import java.time.LocalDateTime

data class ConcertPerformance(
    val id: Long,
    val concert: Concert,
    val maxSeats: Int,
    val performanceDateTime: LocalDateTime
) {
    fun toEntity(): ConcertPerformanceEntity {
        return ConcertPerformanceEntity(
            id = id,
            concert = concert.toEntity(),
            maxSeats = maxSeats,
            performanceDateTime = performanceDateTime
        )
    }
}