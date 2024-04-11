package com.example.hhplusweek3.controller

import com.example.hhplusweek3.application.performanceSeat.PerformanceSeatGetAllAvailableApplication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/performance-seats")
class PerformanceSeatController(
    private val performanceSeatGetAllAvailableApplication: PerformanceSeatGetAllAvailableApplication
) {
    @GetMapping
    fun getAvailablePerformanceSeatsNumbers(@RequestParam concertPerformanceId: Long): ResponseEntity<List<Int>> {
        return ResponseEntity(performanceSeatGetAllAvailableApplication.run(concertPerformanceId), HttpStatus.OK)
    }

    @PostMapping("/{id}/book")
    fun bookPerformanceSeat(@PathVariable id: Long, @RequestParam concertDateId: String) =
        ResponseEntity(HashMap<String, Any>().apply {
            put("performance_seat_id", id)
            put("book_attempt_at", "2024-04-01T00:00:00")
        }, HttpStatus.CREATED)

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
