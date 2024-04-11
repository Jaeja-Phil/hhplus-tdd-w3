package com.example.hhplusweek3.controller.response

import com.example.hhplusweek3.domain.concert.Concert

data class ConcertResponse(
    val id: Long,
    val name: String
) {
    companion object {
        fun from(concert: Concert): ConcertResponse {
            return ConcertResponse(
                id = concert.id,
                name = concert.name
            )
        }
    }
}
