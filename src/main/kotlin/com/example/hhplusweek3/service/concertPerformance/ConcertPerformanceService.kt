package com.example.hhplusweek3.service.concertPerformance

import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
import com.example.hhplusweek3.repository.concertPerformance.ConcertPerformanceRepository
import org.springframework.stereotype.Service

@Service
class ConcertPerformanceService(
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