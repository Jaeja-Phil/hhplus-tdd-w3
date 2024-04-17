package com.example.hhplusweek3.domain.performanceSeat

import com.example.hhplusweek3.domain.concert.ConcertPerformance

data class PerformanceSeatCreateObject(
    val concertPerformance: ConcertPerformance,
    val seatNumber: Int
)
