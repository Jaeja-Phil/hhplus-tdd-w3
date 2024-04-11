package com.example.hhplusweek3.repository.concertPerformance

import com.example.hhplusweek3.entity.concertPerformance.ConcertPerformanceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface ConcertPerformanceJpaRepository : JpaRepository<ConcertPerformanceEntity, Long> {
    /**
     * TODO: find all available concert performances with given requirements:
     *   1. given concertId
     *   2. after given performanceDateTime
     *   3. performance_seats of booked = true is less than max_seats (use left join)
     */
    @Query(
        """
        SELECT cp
        FROM ConcertPerformanceEntity cp
        LEFT JOIN PerformanceSeatEntity ps
        ON cp.id = ps.concertPerformance.id
        WHERE cp.concert.id = :concertId
        AND cp.performanceDateTime >= :performanceDateTime
        AND (SELECT COUNT(ps.id) FROM PerformanceSeatEntity ps WHERE ps.concertPerformance.id = cp.id AND ps.booked = true) < cp.maxSeats
    """
    )
    fun findAllAvailableByConcertIdAndPerformanceDateTimeAfter(
        concertId: Long,
        performanceDateTime: LocalDateTime
    ): List<ConcertPerformanceEntity>
}
