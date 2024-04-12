package com.example.hhplusweek3.repository.performanceSeatBookInfo

import com.example.hhplusweek3.entity.performanceSeatBookInfo.PerformanceSeatBookInfoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PerformanceSeatBookInfoJpaRepository : JpaRepository<PerformanceSeatBookInfoEntity, PerformanceSeatBookInfoEntity> {
    fun findByPerformanceSeatIdAndToken(performanceSeatId: Long, token: String): PerformanceSeatBookInfoEntity?
}
