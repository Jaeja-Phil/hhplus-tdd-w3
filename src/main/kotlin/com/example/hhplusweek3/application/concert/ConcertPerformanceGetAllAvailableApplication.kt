package com.example.hhplusweek3.application.concert

import com.example.hhplusweek3.controller.response.ConcertPerformanceResponse
import com.example.hhplusweek3.domain.concert.ConcertDomain
import org.springframework.stereotype.Component

@Component
class ConcertPerformanceGetAllAvailableApplication(
    private val concertDomain: ConcertDomain
) {

    fun run(concertId: Long): List<ConcertPerformanceResponse> {
        return concertDomain.getAvailableConcertPerformances(concertId)
            .map { ConcertPerformanceResponse.from(it) }
    }
}
