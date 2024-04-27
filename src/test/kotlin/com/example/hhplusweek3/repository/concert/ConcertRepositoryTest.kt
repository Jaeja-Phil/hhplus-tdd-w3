package com.example.hhplusweek3.repository.concert

import com.example.hhplusweek3.domain.concert.Concert
import com.example.hhplusweek3.domain.concert.ConcertCreateObject
import com.example.hhplusweek3.entity.concert.ConcertEntity
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConcertRepositoryTest {
    private val concertJpaRepository = mockk<ConcertJpaRepository>()
    private val concertRepository = ConcertRepository(concertJpaRepository)

    @Test
    fun `findAll - should return list of Concert`() {
        // given
        val concertEntity = ConcertEntity(1, "Concert 1")
        every { concertJpaRepository.findAll() } returns listOf(concertEntity)

        // when
        val result = concertRepository.findAll()

        // then
        assertEquals(1, result.size)
        assertEquals(Concert(1, "Concert 1"), result.first())
    }

    @Test
    fun `save - should return saved concert`() {
        // given
        val concertCreateObject = ConcertCreateObject("Concert 1")
        val concertEntity = ConcertEntity(1, "Concert 1")
        every { concertJpaRepository.save(any<ConcertEntity>()) } returns concertEntity

        // when
        val result = concertRepository.save(concertCreateObject)

        // then
        assertEquals(Concert(1, "Concert 1"), result)
    }
}
