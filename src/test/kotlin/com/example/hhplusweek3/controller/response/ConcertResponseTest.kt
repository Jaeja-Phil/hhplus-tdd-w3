package com.example.hhplusweek3.controller.response

import com.example.hhplusweek3.domain.concert.Concert
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConcertResponseTest {
    @Test
    fun `from - should return ConcertResponse`() {
        // Given
        val concert = Concert(1, "concert")

        // When
        val result = ConcertResponse.from(concert)

        // Then
        assertEquals(ConcertResponse(1, "concert"), result)
    }
}