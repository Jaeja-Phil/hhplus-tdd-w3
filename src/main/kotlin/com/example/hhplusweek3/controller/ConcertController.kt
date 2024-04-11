package com.example.hhplusweek3.controller

import com.example.hhplusweek3.application.concert.ConcertGetAllApplication
import com.example.hhplusweek3.controller.response.ConcertResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/concerts")
class ConcertController(
    private val concertGetAllApplication: ConcertGetAllApplication
) {
    @GetMapping
    fun getConcerts(): ResponseEntity<List<ConcertResponse>> {
        return ResponseEntity(concertGetAllApplication.run(), HttpStatus.OK)
    }
}
