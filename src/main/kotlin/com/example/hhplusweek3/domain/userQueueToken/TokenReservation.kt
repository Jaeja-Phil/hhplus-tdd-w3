package com.example.hhplusweek3.domain.userQueueToken

data class TokenReservation(
    val id: Long,
    val userQueueTokenId: Long,
    val performanceSeatId: Long
)
