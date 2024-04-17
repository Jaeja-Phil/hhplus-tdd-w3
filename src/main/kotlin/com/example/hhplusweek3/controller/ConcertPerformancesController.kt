package com.example.hhplusweek3.controller

import com.example.hhplusweek3.application.concert.ConcertPerformanceGetAllAvailableApplication
import com.example.hhplusweek3.controller.apiSpec.ConcertPerformanceApiSpec
import com.example.hhplusweek3.controller.response.ConcertPerformanceResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/concert-performances")
class ConcertPerformancesController(
    private val concertPerformanceGetAllAvailableApplication: ConcertPerformanceGetAllAvailableApplication
) : ConcertPerformanceApiSpec {
    override fun getConcertPerformances(@RequestParam concertId: Long): ResponseEntity<List<ConcertPerformanceResponse>> {
        return ResponseEntity
            .ok()
            .body(concertPerformanceGetAllAvailableApplication.run(concertId))
    }
}