package com.example.hhplusweek3.controller.response

import com.example.hhplusweek3.domain.concert.ConcertPerformance
import java.time.LocalDateTime

data class ConcertPerformanceResponse(
    val id: Long,
    val concertId: Long,
    val maxSeats: Int,
    val performanceDateTime: LocalDateTime
) {
    companion object {
        fun from(concertPerformance: ConcertPerformance): ConcertPerformanceResponse {
            return ConcertPerformanceResponse(
                id = concertPerformance.id,
                concertId = concertPerformance.concert.id,
                maxSeats = concertPerformance.maxSeats,
                performanceDateTime = concertPerformance.performanceDateTime
            )
        }
    }
}
