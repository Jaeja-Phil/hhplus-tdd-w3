package com.example.hhplusweek3.entity.concert

import com.example.hhplusweek3.domain.concert.Concert
import com.example.hhplusweek3.domain.concert.ConcertCreateObject
import jakarta.persistence.*

@Entity
@Table(name = "concerts")
data class ConcertEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    val name: String
) {
    fun toDomain(): Concert {
        return Concert(
            id = requireNotNull(id),
            name = name
        )
    }

    companion object {
        fun fromCreateObject(concertCreateObject: ConcertCreateObject): ConcertEntity {
            return ConcertEntity(
                id = null,
                name = concertCreateObject.name
            )
        }
    }
}
