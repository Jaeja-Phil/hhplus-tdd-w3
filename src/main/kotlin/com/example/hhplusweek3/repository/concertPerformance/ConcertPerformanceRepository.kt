package com.example.hhplusweek3.repository.concertPerformance

import com.example.hhplusweek3.entity.concertPerformance.ConcertPerformanceEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ConcertPerformanceRepository(
    private val concertPerformanceJpaRepository: ConcertPerformanceJpaRepository
) {
    fun getAvailableConcertPerformances(
        concertId: Long,
        performanceDateTime: LocalDateTime = LocalDateTime.now()
    ): List<ConcertPerformanceEntity> {
        return concertPerformanceJpaRepository.findAllAvailableByConcertIdAndPerformanceDateTimeAfter(
            concertId = concertId,
            performanceDateTime = performanceDateTime
        )
    }
}