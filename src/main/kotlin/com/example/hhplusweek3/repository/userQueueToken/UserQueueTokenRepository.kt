package com.example.hhplusweek3.repository.userQueueToken

import com.example.hhplusweek3.domain.userQueueToken.UserQueueToken
import com.example.hhplusweek3.domain.userQueueToken.UserQueueTokenCreateObject
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenEntity
import org.springframework.stereotype.Repository

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
}
