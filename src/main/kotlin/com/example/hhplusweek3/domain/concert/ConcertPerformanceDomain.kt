package com.example.hhplusweek3.domain.concert

import com.example.hhplusweek3.repository.concert.ConcertPerformanceRepository
import org.springframework.stereotype.Service

@Service
class ConcertPerformanceDomain(
    private val concertPerformanceRepository: ConcertPerformanceRepository
) {
    fun getAvailableConcertPerformances(concertId: Long): List<ConcertPerformance> {
        return concertPerformanceRepository.getAvailableConcertPerformances(concertId)
    }

    fun getConcertById(id: Long): ConcertPerformance? {
        return concertPerformanceRepository.findById(id)
    }

    fun getById(concertPerformanceId: Long): ConcertPerformance? {
        return concertPerformanceRepository.findById(concertPerformanceId)
    }
}