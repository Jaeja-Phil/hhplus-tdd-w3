package com.example.hhplusweek3.entity.performanceSeatBookInfo

import com.example.hhplusweek3.domain.performanceSeatBookInfo.PerformanceSeatBookInfo
import com.example.hhplusweek3.entity.performanceSeat.PerformanceSeatEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import java.time.LocalDateTime

@Entity
@Table(name = "performance_seat_book_infos")
data class PerformanceSeatBookInfoEntity(
    @Id
    @OneToOne
    @JoinColumn(name = "performance_seat_id", referencedColumnName = "id")
    val performanceSeat: PerformanceSeatEntity?,
    val token: String?,
    val bookAttemptAt: LocalDateTime?,
    val bookSuccessAt: LocalDateTime?
) {
    fun toDomain(includePerformanceSeat: Boolean = true): PerformanceSeatBookInfo {
        return PerformanceSeatBookInfo(
            performanceSeat = if (includePerformanceSeat) performanceSeat?.toDomain(!includePerformanceSeat) else null,
            token = token,
            bookAttemptAt = bookAttemptAt,
            bookSuccessAt = bookSuccessAt
        )
    }
}
