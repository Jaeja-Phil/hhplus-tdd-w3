package com.example.hhplusweek3.entity.concert

import com.example.hhplusweek3.domain.concert.Concert
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ConcertEntityTest {
    @Test
    fun `toDomain - should raise error when id is null`() {
        // Given
        val concertEntity = ConcertEntity(null, "concert")

        // When & Then
        assertThrows<IllegalArgumentException> {
            concertEntity.toDomain()
        }
    }

    @Test
    fun `toDomain - should return Concert`() {
        // Given
        val concertEntity = ConcertEntity(1L, "concert")

        // When
        val result = concertEntity.toDomain()

        // Then
        assertEquals(Concert(1L, "concert"), result)
    }
}