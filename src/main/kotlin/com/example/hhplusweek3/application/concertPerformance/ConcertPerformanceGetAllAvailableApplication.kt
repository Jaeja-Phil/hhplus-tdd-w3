package com.example.hhplusweek3.application.concertPerformance

import com.example.hhplusweek3.controller.response.ConcertPerformanceResponse
import com.example.hhplusweek3.service.concertPerformance.ConcertPerformanceService
import org.springframework.stereotype.Component

@Component
class ConcertPerformanceGetAllAvailableApplication(
    private val concertPerformanceService: ConcertPerformanceService
) {

    fun run(concertId: Long): List<ConcertPerformanceResponse> {
        return concertPerformanceService.getAvailableConcertPerformances(concertId)
            .map { ConcertPerformanceResponse.from(it) }
    }
}
