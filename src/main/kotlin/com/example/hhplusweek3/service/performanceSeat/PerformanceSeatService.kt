package com.example.hhplusweek3.service.performanceSeat

import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatCreateObject
import com.example.hhplusweek3.repository.performanceSeat.PerformanceSeatRepository
import org.springframework.stereotype.Service

@Service
class PerformanceSeatService(
    private val performanceSeatRepository: PerformanceSeatRepository
) {
    fun getAllAvailableSeatNumbersByConcertPerformance(concertPerformance: ConcertPerformance): List<Int> {
        val bookedSeatNumbers = performanceSeatRepository
            .findAllByConcertPerformanceIdAndBookedAndUserId(
                concertPerformanceId = concertPerformance.id,
                booked = true,
                userId = null
            )
            .map { it.seatNumber }
        val allSeatNumbers = (1..concertPerformance.maxSeats).toList()

        return allSeatNumbers - bookedSeatNumbers
    }

    fun getBySeatNumberAndConcertPerformanceId(seatNumber: Int, concertPerformanceId: Long): PerformanceSeat? {
        return performanceSeatRepository.findBySeatNumberAndConcertPerformanceId(
            seatNumber = seatNumber,
            concertPerformanceId = concertPerformanceId
        )
    }

    fun createPerformanceSeat(performanceSeatCreateObject: PerformanceSeatCreateObject): PerformanceSeat {
        return performanceSeatRepository.save(performanceSeatCreateObject)
    }

    fun update(performanceSeat: PerformanceSeat): PerformanceSeat {
        return performanceSeatRepository.update(performanceSeat.toEntity())
    }
}
