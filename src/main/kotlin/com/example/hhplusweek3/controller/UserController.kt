package com.example.hhplusweek3.controller

import com.example.hhplusweek3.controller.request.UserChargeBalanceRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserController {
    @PostMapping("/{id}/charge")
    fun charge(@RequestBody request: UserChargeBalanceRequest, @PathVariable id: UUID) =
        ResponseEntity(HashMap<String, Any>().apply {
            put("id", id)
            put("balance", request.amount)
        }, HttpStatus.OK)

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: UUID) =
        ResponseEntity(HashMap<String, Any>().apply {
            put("id", id)
            put("balance", 1000.0)
            put("booked_performance_seats", listOf(
                HashMap<String, Any>().apply {
                    put("id", 1)
                    put("seat_number", 1)
                    put("book_success_at", "2024-04-01T00:00:00")
                    put("concert_performance", HashMap<String, Any>().apply {
                        put("id", 1)
                        put("concert", HashMap<String, Any>().apply {
                            put("id", 1)
                            put("name", "콘서트명")
                        })
                        put("performance_datetime", "2024-04-01T00:00:00")
                    })
                }
            ))
        }, HttpStatus.OK)
}