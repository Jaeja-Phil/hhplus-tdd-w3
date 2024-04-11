package com.example.hhplusweek3.config.advice

import com.example.hhplusweek3.error.BadRequestException
import com.example.hhplusweek3.error.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

data class ErrorResponse(
    val message: String
)

@ControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .internalServerError()
            .body(ErrorResponse(e.message ?: "Internal Server Error"))
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(e: BadRequestException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse(e.message ?: "Bad Request"))
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(e.message ?: "Not Found"),
            HttpStatus.NOT_FOUND
        )
    }
}