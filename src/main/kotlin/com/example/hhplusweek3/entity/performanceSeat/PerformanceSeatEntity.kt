package com.example.hhplusweek3.entity.performanceSeat

import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat
import com.example.hhplusweek3.entity.concertPerformance.ConcertPerformanceEntity
import com.example.hhplusweek3.entity.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "performance_seats",
    uniqueConstraints = [UniqueConstraint(columnNames = ["concert_performance_id", "seatNumber"])]
)
data class PerformanceSeatEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    @ManyToOne
    val concertPerformance: ConcertPerformanceEntity,
    val seatNumber: Int,
    @ManyToOne
    val user: UserEntity?,
    val booked: Boolean = false
) {
    fun toDomain(): PerformanceSeat {
        return PerformanceSeat(
            id = requireNotNull(id),
            concertPerformance = concertPerformance.toDomain(),
            seatNumber = seatNumber,
            user = user?.toDomain(),
            booked = booked
        )
    }
}
