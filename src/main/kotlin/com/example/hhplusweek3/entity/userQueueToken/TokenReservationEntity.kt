package com.example.hhplusweek3.entity.userQueueToken

import com.example.hhplusweek3.domain.userQueueToken.TokenReservation
import com.example.hhplusweek3.domain.userQueueToken.TokenReservationCreateObject
import jakarta.persistence.*

@Entity
@Table(name = "token_reservations")
data class TokenReservationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val userQueueTokenId: Long,
    val performanceSeatId: Long
) {
    fun toDomain(): TokenReservation {
        return TokenReservation(
            id = requireNotNull(id),
            userQueueTokenId = userQueueTokenId,
            performanceSeatId = performanceSeatId
        )
    }

    companion object {
        fun fromCreateObject(tokenReservationCreateObject: TokenReservationCreateObject): TokenReservationEntity {
            return TokenReservationEntity(
                id = null,
                userQueueTokenId = tokenReservationCreateObject.userQueueTokenId,
                performanceSeatId = tokenReservationCreateObject.performanceSeatId
            )
        }
    }
}
