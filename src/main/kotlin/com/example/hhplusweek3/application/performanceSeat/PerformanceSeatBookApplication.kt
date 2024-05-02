package com.example.hhplusweek3.application.performanceSeat

import com.example.hhplusweek3.controller.request.PerformanceSeatBookRequest
import com.example.hhplusweek3.controller.response.PerformanceSeatResponse
import com.example.hhplusweek3.domain.concert.ConcertDomain
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatCreateObject
import com.example.hhplusweek3.domain.performanceSeat.PerformanceSeatDomain
import com.example.hhplusweek3.domain.userQueueToken.UserQueueToken
import com.example.hhplusweek3.domain.userQueueToken.UserQueueTokenDomain
import com.example.hhplusweek3.error.BadRequestException
import com.example.hhplusweek3.error.NotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class PerformanceSeatBookApplication(
    private val concertDomain: ConcertDomain,
    private val performanceSeatDomain: PerformanceSeatDomain,
    private val userQueueTokenDomain: UserQueueTokenDomain
) {
    fun run(request: PerformanceSeatBookRequest, userQueueToken: UserQueueToken): PerformanceSeatResponse {
        // 1. check if concert performance is available and seat number is valid
        val concertPerformance = concertDomain
            .getConcertPerformanceById(request.concertPerformanceId)
            ?: throw NotFoundException("Concert performance is not found.")
        if (request.seatNumber !in 1..concertPerformance.maxSeats) {
            throw BadRequestException("Invalid seat number.")
        }

        // 2. check if userQueueToken is valid
        // - can only try booking 1 seat with 1 token
        val tokenReservations = userQueueTokenDomain.getTokenReservationsByUserQueueTokenId(userQueueToken.id)
        if (tokenReservations.isNotEmpty()) {
            throw BadRequestException("User can only book 1 seat with 1 token.")
        }

        // 3. check for performance seat availability & lock redis key
        performanceSeatDomain.preOccupy(
            concertPerformanceId = request.concertPerformanceId,
            seatNumber = request.seatNumber
        )

        val result = runCatching {
            // 4. get or create performance seat
            val performanceSeat = performanceSeatDomain.getBySeatNumberAndConcertPerformanceId(
                seatNumber = request.seatNumber,
                concertPerformanceId = request.concertPerformanceId
            ) ?: performanceSeatDomain.createPerformanceSeat(
                PerformanceSeatCreateObject(
                    seatNumber = request.seatNumber,
                    concertPerformance = concertPerformance,
                )
            )

            // 5. book performance seat
            userQueueTokenDomain.createTokenReservation(userQueueToken, performanceSeat)
            val bookedPerformanceSeat = performanceSeat.book(userQueueToken)
            val updatedPerformanceSeat = performanceSeatDomain.update(bookedPerformanceSeat)
            PerformanceSeatResponse.from(updatedPerformanceSeat)
        }
        println("result: $result")
        println("result.isSuccess: ${result.isSuccess}")
        println("result.isFailure: ${result.isFailure}")
        result.onFailure {
            println("am i here?")
            println(it)
            println(it.message)
            // 6. release redis key
            performanceSeatDomain.releasePreOccupied(
                concertPerformanceId = request.concertPerformanceId,
                seatNumber = request.seatNumber
            )

            throw it
        }

        return result.getOrThrow()
    }
}
