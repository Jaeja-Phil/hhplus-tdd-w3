package com.example.hhplusweek3.repository.concert

import com.example.hhplusweek3.domain.concert.Concert
import org.springframework.stereotype.Repository

@Repository
class ConcertRepository(
    private val concertJpaRepository: ConcertJpaRepository
) {
    fun findAll(): List<Concert> {
        return concertJpaRepository.findAll().map { it.toDomain() }
    }
}