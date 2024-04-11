package com.example.hhplusweek3.application.performanceSeat

import com.example.hhplusweek3.error.NotFoundException
import com.example.hhplusweek3.service.concertPerformance.ConcertPerformanceService
import com.example.hhplusweek3.service.performanceSeat.PerformanceSeatService
import org.springframework.stereotype.Component

@Component
class PerformanceSeatGetAllAvailableApplication(
    private val performanceSeatService: PerformanceSeatService,
    private val concertPerformanceService: ConcertPerformanceService
) {
    fun run(concertPerformanceId: Long): List<Int> {
        val concertPerformance = concertPerformanceService.getConcertById(concertPerformanceId)
            ?: throw NotFoundException("ConcertPerformance not found.")
        return performanceSeatService.getAllAvailableSeatNumbersByConcertPerformance(concertPerformance)
    }
}
