package com.example.hhplusweek3.application.performanceSeat

import com.example.hhplusweek3.domain.concert.ConcertDomain
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatDomain
import com.example.hhplusweek3.error.NotFoundException
import org.springframework.stereotype.Component

@Component
class PerformanceSeatGetAllAvailableApplication(
    private val performanceSeatDomain: PerformanceSeatDomain,
    private val concertDomain: ConcertDomain
) {
    fun run(concertPerformanceId: Long): List<Int> {
        val concertPerformance = concertDomain.getConcertPerformanceById(concertPerformanceId)
            ?: throw NotFoundException("ConcertPerformance not found.")
        return performanceSeatDomain.getAllAvailableSeatNumbersByConcertPerformance(concertPerformance)
    }
}
