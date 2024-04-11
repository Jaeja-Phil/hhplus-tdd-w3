package com.example.hhplusweek3.domain.concert

import com.example.hhplusweek3.entity.concert.ConcertEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConcertTest {
    @Test
    fun `toEntity - should return ConcertEntity`() {
        // Given
        val concert = Concert(
            id = 1,
            name = "concert"
        )

        // When
        val result = concert.toEntity()

        // Then
        assertEquals(ConcertEntity(id = 1, name = "concert"), result)
    }
}