package com.example.hhplusweek3.domain.performanceSeat

import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.entity.performanceSeat.PerformanceSeatEntity

data class PerformanceSeat(
    val id: Long,
    val concertPerformance: ConcertPerformance,
    val seatNumber: Int,
    val user: User?,
    val booked: Boolean
) {
    fun toEntity(): PerformanceSeatEntity {
        return PerformanceSeatEntity(
            id = id,
            concertPerformance = concertPerformance.toEntity(),
            seatNumber = seatNumber,
            user = user?.toEntity(),
            booked = booked
        )
    }
}
