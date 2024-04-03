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
}