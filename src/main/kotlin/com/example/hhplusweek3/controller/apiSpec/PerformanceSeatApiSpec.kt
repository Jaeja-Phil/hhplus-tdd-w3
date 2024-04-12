package com.example.hhplusweek3.controller.apiSpec

import com.example.hhplusweek3.controller.request.PerformanceSeatBookRequest
import com.example.hhplusweek3.controller.response.PerformanceSeatResponse
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

@Tag(name = "PerformanceSeat", description = "공연 좌석 API")
interface PerformanceSeatApiSpec {
    @GetMapping
    @Operation(
        summary = "공연 좌석 조회",
        description = "예약 가능한 공연 좌석넘버를 조회합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "공연 좌석 조회 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(type = "integer"))
                    )
                ]
            )
        ]
    )
    fun getAvailablePerformanceSeatsNumbers(concertPerformanceId: Long): ResponseEntity<List<Int>>

    @PostMapping("/book")
    @Operation(
        summary = "공연 좌석 예약",
        description = "공연 좌석을 예약대기를 걸어둡니다.",
        requestBody = SwaggerRequestBody(
            description = "공연 좌석 예약대기 요청",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PerformanceSeatBookRequest::class)
                )
            ]
        ),
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "공연 좌석 예약 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PerformanceSeatResponse::class),
                    )
                ]
            )
        ]
    )
    fun bookPerformanceSeat(request: PerformanceSeatBookRequest): ResponseEntity<PerformanceSeatResponse>
}
