package com.example.hhplusweek3.domain.userQueueToken

import com.example.hhplusweek3.domain.User
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenEntity
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenStatus

data class UserQueueToken(
    val id: Long,
    val user: User,
    val token: String,
    val status: UserQueueTokenStatus,
) {
    fun toEntity(): UserQueueTokenEntity {
        return UserQueueTokenEntity(
            id = id,
            user = user.toEntity(),
            token = token,
            status = status,
        )
    }
}
