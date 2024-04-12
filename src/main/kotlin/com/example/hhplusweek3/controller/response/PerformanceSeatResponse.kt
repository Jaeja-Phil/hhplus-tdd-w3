package com.example.hhplusweek3.controller.response

import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat

data class PerformanceSeatResponse(
    val id: Long,
    val concertPerformanceId: Long,
    val seatNumber: Int,
    val booked: Boolean,
) {
    companion object {
        fun from(performanceSeat: PerformanceSeat): PerformanceSeatResponse {
            return PerformanceSeatResponse(
                id = performanceSeat.id,
                concertPerformanceId = performanceSeat.concertPerformance.id,
                seatNumber = performanceSeat.seatNumber,
                booked = performanceSeat.booked
            )
        }
    }
}
