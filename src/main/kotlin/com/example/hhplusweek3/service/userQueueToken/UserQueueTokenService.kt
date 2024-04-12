package com.example.hhplusweek3.service.userQueueToken

import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.domain.userQueueToken.UserQueueToken
import com.example.hhplusweek3.domain.userQueueToken.UserQueueTokenCreateObject
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenStatus
import com.example.hhplusweek3.error.BadRequestException
import com.example.hhplusweek3.repository.userQueueToken.UserQueueTokenRepository
import org.springframework.stereotype.Service

@Service
class UserQueueTokenService(
    private val userQueueTokenRepository: UserQueueTokenRepository
) {
    fun createUserQueueTokenWithValidation(user: User): UserQueueToken {
        validateUserForTokenCreation(user)
        return createUserQueueToken(user)
    }

    fun getByToken(token: String): UserQueueToken? {
        return userQueueTokenRepository.findByToken(token)
    }

    /**
     * TODO:
     *   must implement some sort of locking mechanism to ensure that other distributed services do not create token
     *   for the same user
     */
    fun validateUserForTokenCreation(user: User) {
        userQueueTokenRepository.findByUserIdAndStatusNot(
            userId = user.id,
            status = UserQueueTokenStatus.EXPIRED
        ).let {
            if (it.isNotEmpty()) {
                throw BadRequestException("User is not valid for token creation.")
            }
        }
    }

    private fun createUserQueueToken(user: User): UserQueueToken {
        val token = "${user.id}|${System.currentTimeMillis()}"
        return userQueueTokenRepository.save(UserQueueTokenCreateObject(user = user.toEntity(), token = token))
    }

    fun expire(userQueueToken: UserQueueToken): UserQueueToken {
        return userQueueTokenRepository.update(userQueueToken.copy(status = UserQueueTokenStatus.EXPIRED))
    }
}