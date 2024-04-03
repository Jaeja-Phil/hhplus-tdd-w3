package com.example.hhplusweek3.controller

import com.example.hhplusweek3.controller.request.UserQueueTokenCreateRequest
import com.example.hhplusweek3.controller.response.TokenResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
}