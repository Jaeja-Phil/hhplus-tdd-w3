package com.example.hhplusweek3.repository.userQueueToken

import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenEntity
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserQueueTokenJpaRepository : JpaRepository<UserQueueTokenEntity, Long> {
    fun findByToken(token: String): UserQueueTokenEntity?
    fun findByUserId(userId: UUID): List<UserQueueTokenEntity>
    fun findByUserIdAndStatusNot(userId: UUID, status: UserQueueTokenStatus): List<UserQueueTokenEntity>
}
