package com.example.hhplusweek3.domain.concert

import com.example.hhplusweek3.entity.concert.ConcertPerformanceEntity
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