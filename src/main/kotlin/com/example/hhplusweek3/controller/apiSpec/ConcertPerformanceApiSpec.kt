package com.example.hhplusweek3.controller.apiSpec

import com.example.hhplusweek3.controller.response.ConcertPerformanceResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping


@Tag(name = "ConcertPerformance", description = "콘서트 공연정보 API")
interface ConcertPerformanceApiSpec {
    @GetMapping
    @Operation(
        summary = "콘서트 공연정보 조회",
        description = "특정 콘서트의 예약 가능한 공연정보를 조회합니다.",
        parameters = [
            Parameter(
                name = "concertId",
                description = "콘서트 ID",
                required = true,
                example = "1",
                schema = Schema(type = "integer", format = "int64")
            )
        ],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "콘서트 공연정보 조회 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ConcertPerformanceResponse::class))
                    )
                ]
            )
        ]
    )
    fun getConcertPerformances(concertId: Long): ResponseEntity<List<ConcertPerformanceResponse>>
}
