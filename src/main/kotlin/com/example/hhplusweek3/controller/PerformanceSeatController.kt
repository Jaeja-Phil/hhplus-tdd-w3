package com.example.hhplusweek3.controller

import com.example.hhplusweek3.application.performanceSeat.PerformanceSeatBookApplication
import com.example.hhplusweek3.application.performanceSeat.PerformanceSeatGetAllAvailableApplication
import com.example.hhplusweek3.controller.apiSpec.PerformanceSeatApi
import com.example.hhplusweek3.controller.request.PerformanceSeatBookRequest
import com.example.hhplusweek3.controller.response.PerformanceSeatResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/performance-seats")
class PerformanceSeatController(
    private val performanceSeatGetAllAvailableApplication: PerformanceSeatGetAllAvailableApplication,
    private val performanceSeatBookApplication: PerformanceSeatBookApplication,
) : BaseController(), PerformanceSeatApi {
    override fun getAvailablePerformanceSeatsNumbers(@RequestParam concertPerformanceId: Long): ResponseEntity<List<Int>> {
        return ResponseEntity(performanceSeatGetAllAvailableApplication.run(concertPerformanceId), HttpStatus.OK)
    }

    override fun bookPerformanceSeat(@RequestBody request: PerformanceSeatBookRequest): ResponseEntity<PerformanceSeatResponse> {
        return ResponseEntity(
            performanceSeatBookApplication.run(request, currentUserQueueToken()),
            HttpStatus.CREATED
        )
    }

    @PostMapping("/{id}/pay")
    fun payPerformanceSeat(@PathVariable id: Long, @RequestParam concertDateId: String) =
        ResponseEntity(HashMap<String, Any>().apply {
            put("id", id)
            put("concert_performance_id", 1)
            put("seat_number", 1)
            put("user_id", "UUID")
            put("booked", true)
        }, HttpStatus.CREATED)
}
