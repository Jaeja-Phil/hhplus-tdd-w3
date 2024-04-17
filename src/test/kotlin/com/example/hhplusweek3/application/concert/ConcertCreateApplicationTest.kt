package com.example.hhplusweek3.application.concert

import com.example.hhplusweek3.controller.request.ConcertCreateRequest
import com.example.hhplusweek3.controller.response.ConcertResponse
import com.example.hhplusweek3.entity.concert.ConcertEntity
import com.example.hhplusweek3.domain.concert.ConcertDomain
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConcertCreateApplicationTest {
    private val concertDomain = mockk<ConcertDomain>()
    private val concertCreateApplication = ConcertCreateApplication(concertDomain)

    @Test
    fun `run - should create concert and respond with ConcertResponse`() {
        // given
        val request = ConcertCreateRequest(name = "Concert Name")
        val concertEntity = ConcertEntity(id = 1, name = "Concert Name")
        every { concertDomain.createConcert(name = request.name) } returns concertEntity.toDomain()

        // when
        val response = concertCreateApplication.run(request)

        // then
        assertEquals(ConcertResponse.from(concertEntity.toDomain()), response)
    }
}