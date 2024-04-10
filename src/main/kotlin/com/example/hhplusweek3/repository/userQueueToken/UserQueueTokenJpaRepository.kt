package com.example.hhplusweek3.repository.userQueueToken

import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserQueueTokenJpaRepository : JpaRepository<UserQueueTokenEntity, Long> {
    fun findByToken(token: String): UserQueueTokenEntity?
}
