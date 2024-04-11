package com.example.hhplusweek3.repository.concert

import com.example.hhplusweek3.domain.concert.Concert
import com.example.hhplusweek3.domain.concert.ConcertCreateObject
import com.example.hhplusweek3.entity.concert.ConcertEntity
import org.springframework.stereotype.Repository

@Repository
class ConcertRepository(
    private val concertJpaRepository: ConcertJpaRepository
) {
    fun findAll(): List<Concert> {
        return concertJpaRepository.findAll().map { it.toDomain() }
    }

    fun save(concertCreateObject: ConcertCreateObject): Concert {
        return concertJpaRepository.save(
            ConcertEntity.fromCreateObject(concertCreateObject)
        ).toDomain()
    }
}