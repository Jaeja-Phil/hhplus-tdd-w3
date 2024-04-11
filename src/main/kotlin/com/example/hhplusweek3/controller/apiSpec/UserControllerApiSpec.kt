package com.example.hhplusweek3.controller.apiSpec

import com.example.hhplusweek3.controller.request.UserChargeBalanceRequest
import com.example.hhplusweek3.controller.request.UserCreateRequest
import com.example.hhplusweek3.controller.response.UserResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.*
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@Tag(name = "User", description = "사용자 API")
interface UserControllerApiSpec {
    @PostMapping
    @Operation(
        summary = "사용자 생성",
        description = "사용자를 생성합니다.",
        requestBody = SwaggerRequestBody(
            description = "사용자 생성 요청",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserCreateRequest::class)
                )
            ]
        ),
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "사용자 생성 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserResponse::class),
                    )
                ]
            )
        ]
    )
    fun createUser(request: UserCreateRequest): ResponseEntity<UserResponse>

    @PostMapping("/{id}/charge")
    @Operation(
        summary = "사용자 잔액 충전",
        description = "사용자의 잔액을 충전합니다.",
        requestBody = SwaggerRequestBody(
            description = "사용자 잔액 충전 요청",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserChargeBalanceRequest::class)
                )
            ],
        ),
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "사용자 잔액 충전 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserResponse::class),
                    )
                ]
            )
        ]
    )
    fun charge(
        @RequestBody request: UserChargeBalanceRequest,
        @PathVariable id: UUID
    ): ResponseEntity<UserResponse>
}