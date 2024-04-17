package com.example.hhplusweek3.domain.concert

import com.example.hhplusweek3.domain.concert.Concert
import com.example.hhplusweek3.domain.concert.ConcertCreateObject
import com.example.hhplusweek3.repository.concert.ConcertRepository
import org.springframework.stereotype.Service

@Service
class ConcertDomain(
    private val concertRepository: ConcertRepository
) {
    fun getConcerts(): List<Concert> {
        return concertRepository.findAll()
    }

    fun createConcert(name: String): Concert {
        return concertRepository.save(ConcertCreateObject(name = name))
    }
}
