package com.example.hhplusweek3.repository.userQueueToken

import com.example.hhplusweek3.entity.userQueueToken.TokenReservationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TokenReservationJpaRepository : JpaRepository<TokenReservationEntity, Long> {
    fun findAllByUserQueueTokenId(userQueueTokenId: Long): List<TokenReservationEntity>
}
