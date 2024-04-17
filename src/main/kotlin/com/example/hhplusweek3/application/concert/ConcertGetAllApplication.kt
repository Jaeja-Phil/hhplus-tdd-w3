package com.example.hhplusweek3.application.concert

import com.example.hhplusweek3.controller.response.ConcertResponse
import com.example.hhplusweek3.domain.concert.ConcertDomain
import org.springframework.stereotype.Component

@Component
class ConcertGetAllApplication(
    private val concertDomain: ConcertDomain
) {
    fun run(): List<ConcertResponse> {
        val concerts = concertDomain.getConcerts()
        return concerts.map { ConcertResponse.from(it) }
    }
}