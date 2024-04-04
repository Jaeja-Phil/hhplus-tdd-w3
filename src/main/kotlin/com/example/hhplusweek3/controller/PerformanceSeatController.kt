package com.example.hhplusweek3.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/performance-seats")
class PerformanceSeatController {
    @GetMapping
    fun getPerformanceSeats(@RequestParam concertDateId: String) = ResponseEntity(
        listOf(
            HashMap<String, Any>().apply {
                put("id", 1)
                put("concert_date_id", concertDateId)
                put("seat_number", 1)
                put("booked", false)
            }),
        HttpStatus.OK
    )

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
