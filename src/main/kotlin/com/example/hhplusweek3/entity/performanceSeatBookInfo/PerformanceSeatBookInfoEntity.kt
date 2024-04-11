package com.example.hhplusweek3.entity.performanceSeatBookInfo

import com.example.hhplusweek3.domain.performanceSeatBookInfo.PerformanceSeatBookInfo
import com.example.hhplusweek3.entity.performanceSeat.PerformanceSeatEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "performance_seat_book_infos")
data class PerformanceSeatBookInfoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    @OneToOne
    val performanceSeat: PerformanceSeatEntity,
    val token: String,
    val bookAttemptAt: LocalDateTime?,
    val bookSuccessAt: LocalDateTime?
) {
    fun toDomain(): PerformanceSeatBookInfo {
        return PerformanceSeatBookInfo(
            id = requireNotNull(id),
            performanceSeat = performanceSeat.toDomain(),
            token = token,
            bookAttemptAt = bookAttemptAt!!,
            bookSuccessAt = bookSuccessAt
        )
    }
}
