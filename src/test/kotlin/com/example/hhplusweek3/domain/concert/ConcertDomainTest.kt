package com.example.hhplusweek3.domain.concert

import com.example.hhplusweek3.repository.concert.ConcertRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConcertDomainTest {
    private val concertRepository = mockk<ConcertRepository>()
    private val concertDomain = ConcertDomain(concertRepository)

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
}