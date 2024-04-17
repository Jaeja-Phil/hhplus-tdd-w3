package com.example.hhplusweek3.application.performanceSeat

import com.example.hhplusweek3.error.NotFoundException
import com.example.hhplusweek3.domain.concert.ConcertPerformanceDomain
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatDomain
import org.springframework.stereotype.Component

@Component
class PerformanceSeatGetAllAvailableApplication(
    private val performanceSeatDomain: PerformanceSeatDomain,
    private val concertPerformanceDomain: ConcertPerformanceDomain
) {
    fun run(concertPerformanceId: Long): List<Int> {
        val concertPerformance = concertPerformanceDomain.getConcertById(concertPerformanceId)
            ?: throw NotFoundException("ConcertPerformance not found.")
        return performanceSeatDomain.getAllAvailableSeatNumbersByConcertPerformance(concertPerformance)
    }
}
