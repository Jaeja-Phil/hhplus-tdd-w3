package com.example.hhplusweek3.service.performanceSeat

import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
import com.example.hhplusweek3.repository.performanceSeat.PerformanceSeatRepository
import org.springframework.stereotype.Service

@Service
class PerformanceSeatService(
    private val performanceSeatRepository: PerformanceSeatRepository
) {
    fun getAllAvailableSeatNumbersByConcertPerformance(concertPerformance: ConcertPerformance): List<Int> {
        val bookedSeatNumbers = performanceSeatRepository
            .findAllByConcertPerformanceIdAndBooked(concertPerformanceId = concertPerformance.id, booked = true)
            .filter { it.booked }
            .map { it.seatNumber }
        val allSeatNumbers = (1..concertPerformance.maxSeats).toList()

        return allSeatNumbers - bookedSeatNumbers
    }
}
