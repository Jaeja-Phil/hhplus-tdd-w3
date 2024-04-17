package com.example.hhplusweek3.application.concertPerformance

import com.example.hhplusweek3.controller.response.ConcertPerformanceResponse
import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformanceDomain
import org.springframework.stereotype.Component

@Component
class ConcertPerformanceGetAllAvailableApplication(
    private val concertPerformanceDomain: ConcertPerformanceDomain
) {

    fun run(concertId: Long): List<ConcertPerformanceResponse> {
        return concertPerformanceDomain.getAvailableConcertPerformances(concertId)
            .map { ConcertPerformanceResponse.from(it) }
    }
}
