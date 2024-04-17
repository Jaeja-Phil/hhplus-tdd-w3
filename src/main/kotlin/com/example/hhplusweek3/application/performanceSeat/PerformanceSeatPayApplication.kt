package com.example.hhplusweek3.application.performanceSeat

import com.example.hhplusweek3.controller.response.PerformanceSeatResponse
import com.example.hhplusweek3.domain.userQueueToken.UserQueueToken
import com.example.hhplusweek3.error.NotFoundException
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatDomain
import com.example.hhplusweek3.domain.user.UserDomain
import com.example.hhplusweek3.service.userQueueToken.UserQueueTokenService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class PerformanceSeatPayApplication(
    private val userDomain: UserDomain,
    private val userQueueTokenService: UserQueueTokenService,
    private val performanceSeatDomain: PerformanceSeatDomain,
) {
    fun run(userQueueToken: UserQueueToken, performanceSeatId: Long): PerformanceSeatResponse {
        // 1. check performance seat book info
        val performanceSeat = performanceSeatDomain.getById(performanceSeatId)
                ?: throw NotFoundException("Performance seat not found")

        // TODO: assume the price is 100.0 here, but it should be fetched from actual column
        userDomain.adjustUserBalance(userQueueToken.user, -100.0)
        // update performance seat
        val confirmAppliedPerformanceSeat = performanceSeat.confirm(userQueueToken.token)
        val updatedPerformanceSeat = performanceSeatDomain.update(confirmAppliedPerformanceSeat)
        // expire user-queue-token
        userQueueTokenService.expire(userQueueToken)

        // return booked performance seat information
        return PerformanceSeatResponse.from(updatedPerformanceSeat)
    }
}
