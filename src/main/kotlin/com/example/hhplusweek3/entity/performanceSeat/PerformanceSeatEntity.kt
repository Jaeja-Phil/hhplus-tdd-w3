package com.example.hhplusweek3.entity.performanceSeat

import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatCreateObject
import com.example.hhplusweek3.entity.concertPerformance.ConcertPerformanceEntity
import com.example.hhplusweek3.entity.performanceSeatBookInfo.PerformanceSeatBookInfoEntity
import com.example.hhplusweek3.entity.user.UserEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.AbstractPersistable_.id

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
    val booked: Boolean = false,
    @OneToOne(mappedBy = "performanceSeat", cascade = [CascadeType.ALL])
    val performanceSeatBookInfo: PerformanceSeatBookInfoEntity?
) {
    fun toDomain(includeBookInfo: Boolean = true): PerformanceSeat {
        return PerformanceSeat(
            id = requireNotNull(id),
            concertPerformance = concertPerformance.toDomain(),
            seatNumber = seatNumber,
            user = user?.toDomain(),
            booked = booked,
            performanceSeatBookInfo = if (includeBookInfo) performanceSeatBookInfo?.toDomain() else null
        )
    }

    companion object {
        fun fromCreateObject(performanceSeatCreateObject: PerformanceSeatCreateObject): PerformanceSeatEntity {
            return PerformanceSeatEntity(
                id = null,
                concertPerformance = performanceSeatCreateObject.concertPerformance.toEntity(),
                seatNumber = performanceSeatCreateObject.seatNumber,
                user = null,
                booked = false,
                performanceSeatBookInfo = null
            )
        }

        fun fromDomain(performanceSeat: PerformanceSeat): PerformanceSeatEntity {
            return PerformanceSeatEntity(
                id = performanceSeat.id,
                concertPerformance = performanceSeat.concertPerformance.toEntity(),
                seatNumber = performanceSeat.seatNumber,
                user = performanceSeat.user?.toEntity(),
                booked = performanceSeat.booked,
                performanceSeatBookInfo = performanceSeat.performanceSeatBookInfo?.toEntity()
            )
        }
    }
}
