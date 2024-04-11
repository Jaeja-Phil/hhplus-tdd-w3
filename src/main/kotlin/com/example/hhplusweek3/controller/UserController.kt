package com.example.hhplusweek3.controller

import com.example.hhplusweek3.application.user.UserAdjustBalanceApplication
import com.example.hhplusweek3.application.user.UserCreateApplication
import com.example.hhplusweek3.controller.apiSpec.UserControllerApiSpec
import com.example.hhplusweek3.controller.request.UserChargeBalanceRequest
import com.example.hhplusweek3.controller.request.UserCreateRequest
import com.example.hhplusweek3.controller.response.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userCreateApplication: UserCreateApplication,
    private val userAdjustBalanceApplication: UserAdjustBalanceApplication
) : UserControllerApiSpec {
    @PostMapping
    override fun createUser(@RequestBody request: UserCreateRequest): ResponseEntity<UserResponse> {
        return ResponseEntity(userCreateApplication.run(request), HttpStatus.CREATED)
    }

    @PostMapping("/{id}/charge")
    override fun charge(@RequestBody request: UserChargeBalanceRequest, @PathVariable id: UUID): ResponseEntity<UserResponse> {
        return ResponseEntity(userAdjustBalanceApplication.run(id, request.amount), HttpStatus.OK)
    }


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