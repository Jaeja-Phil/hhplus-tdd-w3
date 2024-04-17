package com.example.hhplusweek3.application.concert

import com.example.hhplusweek3.controller.request.ConcertCreateRequest
import com.example.hhplusweek3.controller.response.ConcertResponse
import com.example.hhplusweek3.domain.concert.ConcertDomain
import org.springframework.stereotype.Component

@Component
class ConcertCreateApplication(
    private val concertDomain: ConcertDomain
) {
    fun run(request: ConcertCreateRequest): ConcertResponse {
        val concert = concertDomain.createConcert(
            name = request.name
        )
        return ConcertResponse.from(concert)
    }
}
