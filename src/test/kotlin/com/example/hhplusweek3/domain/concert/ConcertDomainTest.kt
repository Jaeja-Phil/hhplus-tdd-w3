package com.example.hhplusweek3.domain.concert

import com.example.hhplusweek3.repository.concert.ConcertPerformanceRepository
import com.example.hhplusweek3.repository.concert.ConcertRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ConcertDomainTest {
    private val concertRepository = mockk<ConcertRepository>()
    private val concertPerformanceRepository = mockk<ConcertPerformanceRepository>()
    private val concertDomain = ConcertDomain(concertRepository, concertPerformanceRepository)
    private val now = LocalDateTime.now()

    @Test
    fun `getConcerts - should return list of Concert`() {
        // given
        val concert = Concert(1, "Concert 1")
        every { concertRepository.findAll() } returns listOf(concert)

        // when
        val result = concertDomain.getConcerts()

        // then
        assertEquals(1, result.size)
        assertEquals(Concert(1, "Concert 1"), result.first())
    }

    @Test
    fun `createConcert - should return Concert`() {
        // given
        val concert = Concert(1, "Concert 1")
        every { concertRepository.save(any()) } returns concert

        // when
        val result = concertDomain.createConcert("Concert 1")

        // then
        assertEquals(Concert(1, "Concert 1"), result)
    }

    @Test
    fun `getAvailableConcertPerformances - should return list of ConcertPerformance`() {
        // given
        val concertPerformance = ConcertPerformance(
            id = 1L,
            concert = Concert(1L, "Concert 1"),
            maxSeats = 100,
            performanceDateTime = now
        )
        every { concertPerformanceRepository.getAvailableConcertPerformances(1, any()) } returns listOf(
            concertPerformance
        )

        // when
        val result = concertDomain.getAvailableConcertPerformances(1L)

        // then
        assertEquals(1, result.size)
        assertEquals(concertPerformance, result.first())
    }

    @Test
    fun `getConcertPerformanceById - should return null`() {
        // given
        every { concertPerformanceRepository.findById(1L) } returns null

        // when
        val result = concertDomain.getConcertPerformanceById(1L)

        // then
        assertEquals(null, result)
    }

    @Test
    fun `getConcertPerformanceById - should return ConcertPerformance`() {
        // given
        val concertPerformance = ConcertPerformance(
            id = 1L,
            concert = Concert(1L, "Concert 1"),
            maxSeats = 100,
            performanceDateTime = now
        )
        every { concertPerformanceRepository.findById(1L) } returns concertPerformance

        // when
        val result = concertDomain.getConcertPerformanceById(1L)

        // then
        assertEquals(concertPerformance, result)
    }
}