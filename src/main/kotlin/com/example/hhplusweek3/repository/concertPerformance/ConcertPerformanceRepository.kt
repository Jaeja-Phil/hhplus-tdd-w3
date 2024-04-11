package com.example.hhplusweek3.repository.concertPerformance

import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ConcertPerformanceRepository(
    private val concertPerformanceJpaRepository: ConcertPerformanceJpaRepository
) {
    fun getAvailableConcertPerformances(
        concertId: Long,
        performanceDateTime: LocalDateTime = LocalDateTime.now()
    ): List<ConcertPerformance> {
        return concertPerformanceJpaRepository.findAllAvailableByConcertIdAndPerformanceDateTimeAfter(
            concertId = concertId,
            performanceDateTime = performanceDateTime
        ).map { it.toDomain() }
    }
}