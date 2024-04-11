package com.example.hhplusweek3.repository.concert

import com.example.hhplusweek3.entity.concert.ConcertEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ConcertJpaRepository : JpaRepository<ConcertEntity, Long>
