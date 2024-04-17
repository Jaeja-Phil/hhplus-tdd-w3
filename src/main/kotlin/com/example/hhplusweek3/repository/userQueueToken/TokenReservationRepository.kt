package com.example.hhplusweek3.repository.userQueueToken

import com.example.hhplusweek3.domain.userQueueToken.TokenReservation
import com.example.hhplusweek3.domain.userQueueToken.TokenReservationCreateObject
import com.example.hhplusweek3.entity.userQueueToken.TokenReservationEntity
import org.springframework.stereotype.Repository

@Repository
class TokenReservationRepository(
    private val tokenReservationJpaRepository: TokenReservationJpaRepository
) {
    fun save(tokenReservationCreateObject: TokenReservationCreateObject): TokenReservation {
        return tokenReservationJpaRepository.save(
            TokenReservationEntity.fromCreateObject(tokenReservationCreateObject)
        ).toDomain()
    }

    fun findAllByUserQueueTokenId(userQueueTokenId: Long): List<TokenReservation> {
        return tokenReservationJpaRepository.findAllByUserQueueTokenId(userQueueTokenId).map { it.toDomain() }
    }
}
