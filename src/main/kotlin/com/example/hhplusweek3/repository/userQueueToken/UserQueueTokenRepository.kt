package com.example.hhplusweek3.repository.userQueueToken

import com.example.hhplusweek3.domain.userQueueToken.UserQueueToken
import com.example.hhplusweek3.domain.userQueueToken.UserQueueTokenCreateObject
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenEntity
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenStatus
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserQueueTokenRepository(
    private val userQueueTokenJpaRepository: UserQueueTokenJpaRepository
) {
    fun save(userQueueTokenCreateObject: UserQueueTokenCreateObject): UserQueueToken {
        return userQueueTokenJpaRepository.save(
            UserQueueTokenEntity.fromCreateObject(userQueueTokenCreateObject)
        ).toDomain()
    }

    fun findByToken(token: String): UserQueueToken? {
        return userQueueTokenJpaRepository.findByToken(token)?.toDomain()
    }

    /**
     * use userId instead of user entity object because requiring user object will force the user to load the user object
     */
    fun findByUserId(userId: UUID): List<UserQueueToken> {
        return userQueueTokenJpaRepository.findByUserId(userId).map { it.toDomain() }
    }

    fun findByUserIdAndStatusNot(userId: UUID, status: UserQueueTokenStatus): List<UserQueueToken> {
        return userQueueTokenJpaRepository.findByUserIdAndStatusNot(userId, status).map { it.toDomain() }
    }

    fun update(userQueueToken: UserQueueToken): UserQueueToken {
        return userQueueTokenJpaRepository.save(userQueueToken.toEntity()).toDomain()
    }
}
