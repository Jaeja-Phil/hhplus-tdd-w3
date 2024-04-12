package com.example.hhplusweek3.controller

import com.example.hhplusweek3.application.concert.ConcertCreateApplication
import com.example.hhplusweek3.application.concert.ConcertGetAllApplication
import com.example.hhplusweek3.controller.apiSpec.ConcertApiSpec
import com.example.hhplusweek3.controller.request.ConcertCreateRequest
import com.example.hhplusweek3.controller.response.ConcertResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/concerts")
class ConcertController(
    private val concertGetAllApplication: ConcertGetAllApplication,
    private val concertCreateApplication: ConcertCreateApplication
) : ConcertApiSpec {
    override fun getConcerts(): ResponseEntity<List<ConcertResponse>> {
        return ResponseEntity(concertGetAllApplication.run(), HttpStatus.OK)
    }

    override fun createConcert(@RequestBody request: ConcertCreateRequest): ResponseEntity<ConcertResponse> {
        return ResponseEntity(concertCreateApplication.run(request), HttpStatus.CREATED)
    }
}
