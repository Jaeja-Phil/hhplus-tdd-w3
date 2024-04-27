package com.example.hhplusweek3.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthCheckController {

    @RequestMapping("/check")
    fun check(): String {
        return "TEST PR"
    }
}
