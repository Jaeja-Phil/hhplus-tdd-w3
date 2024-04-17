package com.example.hhplusweek3.application.performanceSeat

import com.example.hhplusweek3.error.NotFoundException
import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformanceDomain
import com.example.hhplusweek3.service.performanceSeat.PerformanceSeatService
import org.springframework.stereotype.Component

@Component
class PerformanceSeatGetAllAvailableApplication(
    private val performanceSeatService: PerformanceSeatService,
    private val concertPerformanceDomain: ConcertPerformanceDomain
) {
    fun run(concertPerformanceId: Long): List<Int> {
        val concertPerformance = concertPerformanceDomain.getConcertById(concertPerformanceId)
            ?: throw NotFoundException("ConcertPerformance not found.")
        return performanceSeatService.getAllAvailableSeatNumbersByConcertPerformance(concertPerformance)
    }
}
