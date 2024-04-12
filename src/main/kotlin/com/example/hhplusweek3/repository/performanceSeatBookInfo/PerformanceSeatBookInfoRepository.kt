package com.example.hhplusweek3.repository.performanceSeatBookInfo

import com.example.hhplusweek3.domain.performanceSeatBookInfo.PerformanceSeatBookInfo
import org.springframework.stereotype.Repository

@Repository
class PerformanceSeatBookInfoRepository(
    private val performanceSeatBookInfoJpaRepository: PerformanceSeatBookInfoJpaRepository
) {
    fun findByPerformanceSeatIdAndToken(performanceSeatId: Long, token: String): PerformanceSeatBookInfo? {
        return performanceSeatBookInfoJpaRepository
            .findByPerformanceSeatIdAndToken(performanceSeatId, token)
            ?.toDomain()
    }
}
