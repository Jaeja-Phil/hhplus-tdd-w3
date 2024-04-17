package com.example.hhplusweek3.domain.performanceSeat

import com.example.hhplusweek3.domain.concertPerformance.ConcertPerformance
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
    val bookAttemptAt: LocalDateTime?,
    val bookSuccessAt: LocalDateTime?,
) {
    fun toEntity(): PerformanceSeatEntity {
        return PerformanceSeatEntity(
            id = id,
            concertPerformance = concertPerformance.toEntity(),
            seatNumber = seatNumber,
            user = user?.toEntity(),
            booked = booked,
            bookAttemptAt = bookAttemptAt,
            bookSuccessAt = bookSuccessAt,
        )
    }

    fun isAvailable(): Boolean {
        val now = LocalDateTime.now()

        return !(booked || bookSuccessAt != null || concertPerformance.performanceDateTime.isBefore(now) ||
                bookAttemptAt?.isAfter(now.minusMinutes(5)) == true)
    }

    fun book(userQueueToken: UserQueueToken): PerformanceSeat {
        if (!isAvailable()) {
            throw BadRequestException("Performance seat is not available to book.")
        }

        val now = LocalDateTime.now()
        return copy(
            user = userQueueToken.user,
            bookAttemptAt = now
        )
    }

    fun confirm(token: String): PerformanceSeat {
        // TODO: add performanceSeat information to userQueueToken
        /*if (performanceSeatBookInfo?.token != token) {
            throw BadRequestException("Invalid token.")
        }

        if (!performanceSeatBookInfo.isConfirmable()) {
            throw BadRequestException("Performance seat is not confirmable.")
        }*/
        return copy(
            booked = true,
            bookSuccessAt = LocalDateTime.now()
        )
    }
}
