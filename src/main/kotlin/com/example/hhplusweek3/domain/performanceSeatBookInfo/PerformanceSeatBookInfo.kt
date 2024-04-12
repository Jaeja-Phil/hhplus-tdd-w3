package com.example.hhplusweek3.domain.performanceSeatBookInfo

import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat
import com.example.hhplusweek3.entity.performanceSeatBookInfo.PerformanceSeatBookInfoEntity
import java.time.LocalDateTime

data class PerformanceSeatBookInfo(
    val performanceSeat: PerformanceSeat?,
    val token: String?,
    val bookAttemptAt: LocalDateTime?,
    val bookSuccessAt: LocalDateTime?
) {
    fun toEntity(): PerformanceSeatBookInfoEntity {
        return PerformanceSeatBookInfoEntity(
            id = performanceSeat?.id,
            performanceSeat = performanceSeat?.toEntity(),
            token = token,
            bookAttemptAt = bookAttemptAt,
            bookSuccessAt = bookSuccessAt
        )
    }

    fun isConfirmable(): Boolean {
        /**
         * can confirm booking if
         * - bookSuccessAt is null
         * - bookAttemptAt is 5 minutes before now
         */
        return bookSuccessAt == null && bookAttemptAt?.plusMinutes(5)?.isBefore(LocalDateTime.now()) ?: false
    }
}
