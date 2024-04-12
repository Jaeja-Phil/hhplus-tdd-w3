package com.example.hhplusweek3.repository.performanceSeat

import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatCreateObject
import com.example.hhplusweek3.entity.performanceSeat.PerformanceSeatEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class PerformanceSeatRepository(
    private val performanceSeatJpaRepository: PerformanceSeatJpaRepository
) {
    fun findAllByConcertPerformanceIdAndBookedAndUserId(
        concertPerformanceId: Long,
        booked: Boolean,
        userId: UUID?
    ): List<PerformanceSeat> {
        return performanceSeatJpaRepository
            .findAllByConcertPerformanceIdAndBookedAndUserId(
                concertPerformanceId = concertPerformanceId,
                booked = booked,
                userId = userId
            )
            .map { it.toDomain() }
    }

    fun findBySeatNumberAndConcertPerformanceId(seatNumber: Int, concertPerformanceId: Long): PerformanceSeat? {
        return performanceSeatJpaRepository.findBySeatNumberAndConcertPerformanceId(
            seatNumber = seatNumber,
            concertPerformanceId = concertPerformanceId
        )?.toDomain()
    }

    fun save(performanceSeatCreateObject: PerformanceSeatCreateObject): PerformanceSeat {
        return performanceSeatJpaRepository.save(
            PerformanceSeatEntity.fromCreateObject(performanceSeatCreateObject)
        ).toDomain()
    }

    fun update(toEntity: PerformanceSeatEntity): PerformanceSeat {
        return performanceSeatJpaRepository.save(toEntity).toDomain()
    }
}