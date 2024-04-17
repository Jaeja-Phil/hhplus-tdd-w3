package com.example.hhplusweek3.application.performanceSeat

import com.example.hhplusweek3.controller.request.PerformanceSeatBookRequest
import com.example.hhplusweek3.controller.response.PerformanceSeatResponse
import com.example.hhplusweek3.domain.concert.ConcertDomain
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatCreateObject
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatDomain
import com.example.hhplusweek3.domain.userQueueToken.UserQueueToken
import com.example.hhplusweek3.error.BadRequestException
import com.example.hhplusweek3.error.NotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class PerformanceSeatBookApplication(
    private val concertDomain: ConcertDomain,
    private val performanceSeatDomain: PerformanceSeatDomain
) {
    fun run(request: PerformanceSeatBookRequest, userQueueToken: UserQueueToken): PerformanceSeatResponse {
        // 1. check if concert performance is available
        val concertPerformance = concertDomain
            .getConcertPerformanceById(request.concertPerformanceId)
            ?: throw NotFoundException("Concert performance is not found.")

        // 2. check if performance seat is available
        var performanceSeat = performanceSeatDomain.getBySeatNumberAndConcertPerformanceId(
            seatNumber = request.seatNumber,
            concertPerformanceId = request.concertPerformanceId
        )
        if (performanceSeat != null && performanceSeat.isAvailable().not()) {
            throw BadRequestException("Performance seat is not available.")
        }

        // 3. book performance seat
        if (performanceSeat == null) {
            performanceSeat = performanceSeatDomain.createPerformanceSeat(
                PerformanceSeatCreateObject(
                    seatNumber = request.seatNumber,
                    concertPerformance = concertPerformance,
                )
            )
        }
        val bookedPerformanceSeat = performanceSeat.book(userQueueToken)
        val updatedPerformanceSeat = performanceSeatDomain.update(bookedPerformanceSeat)
        return PerformanceSeatResponse.from(updatedPerformanceSeat)
    }
}
