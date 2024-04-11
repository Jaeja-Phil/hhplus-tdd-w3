package com.example.hhplusweek3.application.concert

import com.example.hhplusweek3.controller.response.ConcertResponse
import com.example.hhplusweek3.service.concert.ConcertService
import org.springframework.stereotype.Component

@Component
class ConcertGetAllApplication(
    private val concertService: ConcertService
) {
    fun run(): List<ConcertResponse> {
        val concerts = concertService.getConcerts()
        return concerts.map { ConcertResponse.from(it) }
    }
}