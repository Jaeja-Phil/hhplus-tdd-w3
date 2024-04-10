package com.example.hhplusweek3.domain.userQueueToken

import com.example.hhplusweek3.entity.user.UserEntity

data class UserQueueTokenCreateObject(
    val user: UserEntity,
    val token: String,
)
