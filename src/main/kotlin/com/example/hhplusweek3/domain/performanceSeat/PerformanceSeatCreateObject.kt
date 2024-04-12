package com.example.hhplusweek3.domain.performanceSeat

import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance

data class PerformanceSeatCreateObject(
    val concertPerformance: ConcertPerformance,
    val seatNumber: Int
)
