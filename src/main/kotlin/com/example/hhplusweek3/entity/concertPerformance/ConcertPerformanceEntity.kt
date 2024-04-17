package com.example.hhplusweek3.entity.concertPerformance

import com.example.hhplusweek3.domain.concert.ConcertPerformance
import com.example.hhplusweek3.entity.concert.ConcertEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "concert_performances")
data class ConcertPerformanceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    @ManyToOne
    val concert: ConcertEntity,
    val maxSeats: Int,
    val performanceDateTime: LocalDateTime
) {
    fun toDomain(): ConcertPerformance {
        return ConcertPerformance(
            id = requireNotNull(id),
            concert = concert.toDomain(),
            maxSeats = maxSeats,
            performanceDateTime = performanceDateTime
        )
    }
}
