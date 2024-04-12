package com.example.hhplusweek3.controller.apiSpec

import com.example.hhplusweek3.controller.request.UserQueueTokenCreateRequest
import com.example.hhplusweek3.controller.response.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping

@Tag(name = "UserQueueToken", description = "사용자 대기열 토큰 API")
interface UserQueueTokenApiSpec {
    @PostMapping
    @Operation(
        summary = "사용자 대기열 토큰 생성",
        description = "사용자 대기열 토큰을 생성합니다.",
        requestBody = RequestBody(
            description = "사용자 대기열 토큰 생성 요청",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserQueueTokenCreateRequest::class)
                )
            ]
        ),
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "사용자 대기열 토큰 생성 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = TokenResponse::class),
                    )
                ]
            )
        ]
    )
    fun create(request: UserQueueTokenCreateRequest): ResponseEntity<TokenResponse>
}
