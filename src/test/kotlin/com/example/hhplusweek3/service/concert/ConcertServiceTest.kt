package com.example.hhplusweek3.service.concert

import com.example.hhplusweek3.domain.concert.Concert
import com.example.hhplusweek3.repository.concert.ConcertRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConcertServiceTest {
    private val concertRepository = mockk<ConcertRepository>()
    private val concertService = ConcertService(concertRepository)

    @Test
    fun `getConcerts - should return list of Concert`() {
        // given
         val concert = Concert(1, "Concert 1")
         every { concertRepository.findAll() } returns listOf(concert)

        // when
         val result = concertService.getConcerts()

        // then
         assertEquals(1, result.size)
         assertEquals(Concert(1, "Concert 1"), result.first())
    }
}