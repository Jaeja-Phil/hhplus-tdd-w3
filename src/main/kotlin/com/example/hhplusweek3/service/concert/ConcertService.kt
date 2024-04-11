package com.example.hhplusweek3.service.concert

import com.example.hhplusweek3.domain.concert.Concert
import com.example.hhplusweek3.repository.concert.ConcertRepository
import org.springframework.stereotype.Service

@Service
class ConcertService(
    private val concertRepository: ConcertRepository
) {
    fun getConcerts(): List<Concert> {
        return concertRepository.findAll()
    }
}
