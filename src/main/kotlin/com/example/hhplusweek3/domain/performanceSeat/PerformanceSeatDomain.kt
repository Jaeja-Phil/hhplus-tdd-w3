package com.example.hhplusweek3.domain.performanceSeat

import com.example.hhplusweek3.domain.concert.ConcertPerformance
import com.example.hhplusweek3.entity.performanceSeat.PerformanceSeatEntity
import com.example.hhplusweek3.error.PerformanceSeatOccupiedException
import com.example.hhplusweek3.repository.performanceSeat.PerformanceSeatRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class PerformanceSeatDomain(
    private val performanceSeatRepository: PerformanceSeatRepository,
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun getAllAvailableSeatNumbersByConcertPerformance(concertPerformance: ConcertPerformance): List<Int> {
        val bookedSeatNumbers = performanceSeatRepository
            .findAllByConcertPerformanceIdAndBookedAndUserId(
                concertPerformanceId = concertPerformance.id,
                booked = true,
                userId = null
            )
            .map { it.seatNumber }
        val allSeatNumbers = (1..concertPerformance.maxSeats).toList()

        return allSeatNumbers - bookedSeatNumbers
    }

    fun getBySeatNumberAndConcertPerformanceId(seatNumber: Int, concertPerformanceId: Long): PerformanceSeat? {
        return performanceSeatRepository.findBySeatNumberAndConcertPerformanceId(
            seatNumber = seatNumber,
            concertPerformanceId = concertPerformanceId
        )
    }

    fun createPerformanceSeat(performanceSeatCreateObject: PerformanceSeatCreateObject): PerformanceSeat {
        return performanceSeatRepository.save(performanceSeatCreateObject)
    }

    fun update(performanceSeat: PerformanceSeat): PerformanceSeat {
        return performanceSeatRepository.update(PerformanceSeatEntity.fromDomain(performanceSeat))
    }

    fun getById(performanceSeatId: Long): PerformanceSeat? {
        return performanceSeatRepository.findById(performanceSeatId)
    }

    fun preOccupy(concertPerformanceId: Long, seatNumber: Int) {
        // 1. check if redis key exists and atomically set the key if not exists
        // should set expiration time of 5 minutes
        val key = "performance_seat:${concertPerformanceId}:${seatNumber}"
        val value = "pre-occupied"
        val isSet = redisTemplate.opsForValue().setIfAbsent(key, value, Duration.ofMinutes(5))
        if (isSet == false) {
            throw PerformanceSeatOccupiedException("Performance seat is already occupied.")
        }

        // 2. check if the seat is already occupied in db
        val performanceSeat = getBySeatNumberAndConcertPerformanceId(seatNumber, concertPerformanceId)
        if (performanceSeat != null && performanceSeat.isAvailable().not()) {
            redisTemplate.delete(key)
            throw PerformanceSeatOccupiedException("Performance seat is already occupied.")
        }
    }

    fun releasePreOccupied(concertPerformanceId: Long, seatNumber: Int) {
        val key = "performance_seat:${concertPerformanceId}:${seatNumber}"
        redisTemplate.delete(key)
    }
}
