package com.example.hhplusweek3.application.concert

import com.example.hhplusweek3.controller.response.ConcertResponse
import com.example.hhplusweek3.domain.concert.Concert
import com.example.hhplusweek3.service.concert.ConcertService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConcertGetAllApplicationTest {
    private val concertService = mockk<ConcertService>()
    private val concertGetAllApplication = ConcertGetAllApplication(concertService)

    @Test
    fun `run - should return list of ConcertResponse`() {
        // given
        val concerts = listOf(Concert(1, "Concert 1"), Concert(2, "Concert 2"))
        every { concertService.getConcerts() } returns concerts

        // when
        val result = concertGetAllApplication.run()

        // then
        assertEquals(2, result.size)
        assertEquals(ConcertResponse(1, "Concert 1"), result.first())
        assertEquals(ConcertResponse(2, "Concert 2"), result.last())
    }
}