package com.example.hhplusweek3.domain.userQueueToken

import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeat
import com.example.hhplusweek3.domain.user.User
import com.example.hhplusweek3.entity.userQueueToken.UserQueueTokenStatus
import com.example.hhplusweek3.error.BadRequestException
import com.example.hhplusweek3.repository.userQueueToken.TokenReservationRepository
import com.example.hhplusweek3.repository.userQueueToken.UserQueueTokenRepository
import org.springframework.stereotype.Service

@Service
class UserQueueTokenDomain(
    private val userQueueTokenRepository: UserQueueTokenRepository,
    private val tokenReservationRepository: TokenReservationRepository
) {
    fun createUserQueueTokenWithValidation(user: User): UserQueueToken {
        validateUserForUserQueueTokenCreation(user)
        return createUserQueueToken(user)
    }

    fun getUseQueueTokenByToken(token: String): UserQueueToken? {
        return userQueueTokenRepository.findByToken(token)
    }

    /**
     * TODO:
     *   must implement some sort of locking mechanism to ensure that other distributed services do not create token
     *   for the same user
     */
    fun validateUserForUserQueueTokenCreation(user: User) {
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

    fun expireUserQueueToken(userQueueToken: UserQueueToken): UserQueueToken {
        return userQueueTokenRepository.update(userQueueToken.copy(status = UserQueueTokenStatus.EXPIRED))
    }

    fun createTokenReservation(userQueueToken: UserQueueToken, performanceSeat: PerformanceSeat): TokenReservation {
        return tokenReservationRepository.save(TokenReservationCreateObject(
            userQueueTokenId = userQueueToken.id,
            performanceSeatId = performanceSeat.id
        ))
    }

    fun getTokenReservationsByUserQueueTokenId(userQueueTokenId: Long): List<TokenReservation> {
        return tokenReservationRepository.findAllByUserQueueTokenId(userQueueTokenId)
    }
}