package com.example.hhplusweek3.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/concert-performances")
class ConcertPerformancesController {
    @GetMapping("/{concertId}")
    fun getConcertPerformances(@PathVariable concertId: String) = ResponseEntity(
        listOf(
            HashMap<String, Any>().apply {
                put("id", 1)
                put("concert_id", concertId)
                put("max_seats", 50)
                put("performance_datetime", "2024-04-01T00:00:00")
            }),
        HttpStatus.OK
    )
}