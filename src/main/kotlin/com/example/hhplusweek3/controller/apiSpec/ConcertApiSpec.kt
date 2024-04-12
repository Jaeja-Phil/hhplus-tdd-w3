package com.example.hhplusweek3.controller.apiSpec

import com.example.hhplusweek3.controller.request.ConcertCreateRequest
import com.example.hhplusweek3.controller.response.ConcertResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@Tag(name = "Concert", description = "콘서트 API")
interface ConcertApiSpec {
    @GetMapping
    @Operation(
        summary = "콘서트 조회",
        description = "모든 콘서트를 조회합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "콘서트 조회 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ConcertResponse::class))
                    )
                ]
            )
        ]
    )
    fun getConcerts(): ResponseEntity<List<ConcertResponse>>

    @PostMapping
    @Operation(
        summary = "콘서트 생성",
        description = "콘서트를 생성합니다.",
        requestBody = SwaggerRequestBody(
            description = "콘서트 생성 요청",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ConcertCreateRequest::class)
                )
            ]
        ),
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "콘서트 생성 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ConcertResponse::class),
                    )
                ]
            )
        ]
    )
    fun createConcert(request: ConcertCreateRequest): ResponseEntity<ConcertResponse>
}
