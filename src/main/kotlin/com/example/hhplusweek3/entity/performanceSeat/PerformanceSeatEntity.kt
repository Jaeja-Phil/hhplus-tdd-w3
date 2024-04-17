package com.example.hhplusweek3.entity.performanceSeat

import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatCreateObject
import com.example.hhplusweek3.entity.concertPerformance.ConcertPerformanceEntity
import com.example.hhplusweek3.entity.user.UserEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "performance_seats",
    uniqueConstraints = [UniqueConstraint(columnNames = ["concert_performance_id", "seat_number"])]
)
data class PerformanceSeatEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    @ManyToOne
    val concertPerformance: ConcertPerformanceEntity,
    val seatNumber: Int,
    @ManyToOne
    val user: UserEntity? = null,
    val booked: Boolean = false,
    val bookAttemptAt: LocalDateTime? = null,
    val bookSuccessAt: LocalDateTime? = null,
) {
    fun toDomain(): PerformanceSeat {
        return PerformanceSeat(
            id = requireNotNull(id),
            concertPerformance = concertPerformance.toDomain(),
            seatNumber = seatNumber,
            user = user?.toDomain(),
            booked = booked,
            bookAttemptAt = bookAttemptAt,
            bookSuccessAt = bookSuccessAt,
        )
    }

    companion object {
        fun fromCreateObject(performanceSeatCreateObject: PerformanceSeatCreateObject): PerformanceSeatEntity {
            return PerformanceSeatEntity(
                id = null,
                concertPerformance = performanceSeatCreateObject.concertPerformance.toEntity(),
                seatNumber = performanceSeatCreateObject.seatNumber,
                booked = false,
            )
        }

        fun fromDomain(performanceSeat: PerformanceSeat): PerformanceSeatEntity {
            return PerformanceSeatEntity(
                id = performanceSeat.id,
                concertPerformance = performanceSeat.concertPerformance.toEntity(),
                seatNumber = performanceSeat.seatNumber,
                user = performanceSeat.user?.toEntity(),
                booked = performanceSeat.booked,
                bookAttemptAt = performanceSeat.bookAttemptAt,
                bookSuccessAt = performanceSeat.bookSuccessAt,
            )
        }
    }
}
