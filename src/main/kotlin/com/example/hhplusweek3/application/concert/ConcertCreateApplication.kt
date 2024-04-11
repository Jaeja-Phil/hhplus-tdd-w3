package com.example.hhplusweek3.application.concert

import com.example.hhplusweek3.controller.request.ConcertCreateRequest
import com.example.hhplusweek3.controller.response.ConcertResponse
import com.example.hhplusweek3.service.concert.ConcertService
import org.springframework.stereotype.Component

@Component
class ConcertCreateApplication(
    private val concertService: ConcertService
) {
    fun run(request: ConcertCreateRequest): ConcertResponse {
        val concert = concertService.createConcert(
            name = request.name
        )
        return ConcertResponse.from(concert)
    }
}
