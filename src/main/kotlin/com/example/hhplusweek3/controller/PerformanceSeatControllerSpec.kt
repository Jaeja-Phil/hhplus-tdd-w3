package com.example.hhplusweek3.controller

import com.example.hhplusweek3.application.performanceSeat.PerformanceSeatBookApplication
import com.example.hhplusweek3.application.performanceSeat.PerformanceSeatGetAllAvailableApplication
import com.example.hhplusweek3.application.performanceSeat.PerformanceSeatPayApplication
import com.example.hhplusweek3.controller.apiSpec.PerformanceSeatApiSpec
import com.example.hhplusweek3.controller.request.PerformanceSeatBookRequest
import com.example.hhplusweek3.controller.response.PerformanceSeatResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/performance-seats")
class PerformanceSeatControllerSpec(
    private val performanceSeatGetAllAvailableApplication: PerformanceSeatGetAllAvailableApplication,
    private val performanceSeatBookApplication: PerformanceSeatBookApplication,
    private val performanceSeatPayApplication: PerformanceSeatPayApplication
) : BaseController(), PerformanceSeatApiSpec {
    override fun getAvailablePerformanceSeatsNumbers(@RequestParam concertPerformanceId: Long): ResponseEntity<List<Int>> {
        return ResponseEntity(performanceSeatGetAllAvailableApplication.run(concertPerformanceId), HttpStatus.OK)
    }

    override fun bookPerformanceSeat(@RequestBody request: PerformanceSeatBookRequest): ResponseEntity<PerformanceSeatResponse> {
        return ResponseEntity(
            performanceSeatBookApplication.run(request, currentUserQueueToken()),
            HttpStatus.CREATED
        )
    }

    override fun payPerformanceSeat(@PathVariable id: Long): ResponseEntity<PerformanceSeatResponse> {
        return ResponseEntity(
            performanceSeatPayApplication.run(currentUserQueueToken(), id),
            HttpStatus.OK
        )
    }
}
