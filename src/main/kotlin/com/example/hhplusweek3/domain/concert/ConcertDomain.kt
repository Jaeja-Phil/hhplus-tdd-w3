package com.example.hhplusweek3.domain.concert

import com.example.hhplusweek3.repository.concert.ConcertPerformanceRepository
import com.example.hhplusweek3.repository.concert.ConcertRepository
import org.springframework.stereotype.Service

@Service
class ConcertDomain(
    private val concertRepository: ConcertRepository,
    private val concertPerformanceRepository: ConcertPerformanceRepository
) {
    fun getConcerts(): List<Concert> {
        return concertRepository.findAll()
    }

    fun createConcert(name: String): Concert {
        return concertRepository.save(ConcertCreateObject(name = name))
    }

    fun getAvailableConcertPerformances(concertId: Long): List<ConcertPerformance> {
        return concertPerformanceRepository.getAvailableConcertPerformances(concertId)
    }

    fun getConcertPerformanceById(id: Long): ConcertPerformance? {
        return concertPerformanceRepository.findById(id)
    }
}
