package com.example.hhplusweek3.repository.concert

import com.example.hhplusweek3.domain.concert.ConcertPerformance
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

    fun findById(id: Long): ConcertPerformance? {
        return concertPerformanceJpaRepository.findById(id).orElse(null)?.toDomain()
    }
}