package com.example.hhplusweek3.controller

import com.example.hhplusweek3.controller.request.UserQueueTokenCreateRequest
import com.example.hhplusweek3.controller.response.TokenResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user-token-queues")
class UserQueueTokenController {
    @PostMapping
    fun create(@RequestBody request: UserQueueTokenCreateRequest): ResponseEntity<TokenResponse> {
        return ResponseEntity(TokenResponse(token = "some-token"), HttpStatus.CREATED)
    }

    @GetMapping("/token-info")
    fun getTokenInfo() = ResponseEntity(HashMap<String, Any?>().apply {
        put("user_id", "UUID")
        put("status", "IN_QUEUE")
        put("performance_seat_book_info", HashMap<String, Any>().apply {
                put("id", 1)
                put("performance_seat_id", 1)
                put("book_attempt_at", "2024-04-01T00:00:00")
                put("book_success_at", null)
        })
    }, HttpStatus.OK)

}