package com.example.hhplusweek3.entity.concert

import com.example.hhplusweek3.domain.concert.Concert
import jakarta.persistence.*

@Entity
@Table(name = "concerts")
data class ConcertEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val name: String
) {
    fun toDomain(): Concert {
        return Concert(
            id = requireNotNull(id),
            name = name
        )
    }
}
