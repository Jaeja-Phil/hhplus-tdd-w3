package com.example.hhplusweek3.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/concerts")
class ConcertController {
    @GetMapping
    fun getConcerts() = ResponseEntity(listOf(
        HashMap<String, Any>().apply {
            put("id", 1)
            put("name", "콘서트명")
        }
    ), HttpStatus.OK)
}
