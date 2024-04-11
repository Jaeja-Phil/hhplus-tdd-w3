package com.example.hhplusweek3.domain.concert

import com.example.hhplusweek3.entity.concert.ConcertEntity

data class Concert(
    val id: Long,
    val name: String
) {
    fun toEntity(): ConcertEntity {
        return ConcertEntity(
            id = id,
            name = name
        )
    }
}