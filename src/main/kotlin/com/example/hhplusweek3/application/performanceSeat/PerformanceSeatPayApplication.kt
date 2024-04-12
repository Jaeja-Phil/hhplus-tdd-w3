package com.example.hhplusweek3.application.performanceSeat

import com.example.hhplusweek3.controller.response.PerformanceSeatResponse
import com.example.hhplusweek3.domain.userQueueToken.UserQueueToken
import com.example.hhplusweek3.error.NotFoundException
import com.example.hhplusweek3.service.performanceSeat.PerformanceSeatService
import com.example.hhplusweek3.service.user.UserService
import com.example.hhplusweek3.service.userQueueToken.UserQueueTokenService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class PerformanceSeatPayApplication(
    private val userService: UserService,
    private val userQueueTokenService: UserQueueTokenService,
    private val performanceSeatService: PerformanceSeatService,
) {
    fun run(userQueueToken: UserQueueToken, performanceSeatId: Long): PerformanceSeatResponse {
        // 1. check performance seat book info
        val performanceSeat = performanceSeatService.getById(performanceSeatId)
                ?: throw NotFoundException("Performance seat not found")

        // TODO: assume the price is 100.0 here, but it should be fetched from actual column
        userService.adjustUserBalance(userQueueToken.user, -100.0)
        // update performance seat
        val confirmAppliedPerformanceSeat = performanceSeat.confirm(userQueueToken.token)
        val updatedPerformanceSeat = performanceSeatService.update(confirmAppliedPerformanceSeat)
        // expire user-queue-token
        userQueueTokenService.expire(userQueueToken)

        // return booked performance seat information
        return PerformanceSeatResponse.from(updatedPerformanceSeat)
    }
}
