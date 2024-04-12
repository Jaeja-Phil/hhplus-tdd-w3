package com.example.hhplusweek3.domain.performanceSeat

import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
import com.example.hhplusweek3.domain.performanceSeatBookInfo.PerformanceSeatBookInfo
import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.domain.userQueueToken.UserQueueToken
import com.example.hhplusweek3.entity.performanceSeat.PerformanceSeatEntity
import com.example.hhplusweek3.error.BadRequestException
import java.time.LocalDateTime

data class PerformanceSeat(
    val id: Long,
    val concertPerformance: ConcertPerformance,
    val seatNumber: Int,
    val user: User?,
    val booked: Boolean,
    val performanceSeatBookInfo: PerformanceSeatBookInfo?
) {
    fun toEntity(): PerformanceSeatEntity {
        return PerformanceSeatEntity(
            id = id,
            concertPerformance = concertPerformance.toEntity(),
            seatNumber = seatNumber,
            user = user?.toEntity(),
            booked = booked,
            performanceSeatBookInfo = performanceSeatBookInfo?.toEntity()
        )
    }

    fun isAvailable(): Boolean {
        val now = LocalDateTime.now()

        if (booked ||
            concertPerformance.performanceDateTime.isBefore(now) ||
            performanceSeatBookInfo?.bookSuccessAt != null) {
            return false
        }

        if (performanceSeatBookInfo?.bookAttemptAt != null &&
            performanceSeatBookInfo.bookAttemptAt.isAfter(now.minusMinutes(5))) {
            return false
        }

        return true
    }

    fun book(userQueueToken: UserQueueToken): PerformanceSeat {
        if (!isAvailable()) {
            throw BadRequestException("Performance seat is not available to book.")
        }

        val now = LocalDateTime.now()
        return copy(
            user = userQueueToken.user,
            performanceSeatBookInfo = PerformanceSeatBookInfo(
                performanceSeat = this,
                token = userQueueToken.token,
                bookAttemptAt = now,
                bookSuccessAt = null
            )
        )
    }
}
