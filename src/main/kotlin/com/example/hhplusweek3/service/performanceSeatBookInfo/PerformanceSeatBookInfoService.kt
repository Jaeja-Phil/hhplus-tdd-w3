package com.example.hhplusweek3.service.performanceSeatBookInfo

import com.example.hhplusweek3.domain.performanceSeatBookInfo.PerformanceSeatBookInfo
import com.example.hhplusweek3.repository.performanceSeatBookInfo.PerformanceSeatBookInfoRepository
import org.springframework.stereotype.Service

@Service
class PerformanceSeatBookInfoService(
    private val performanceSeatBookInfoRepository: PerformanceSeatBookInfoRepository
) {
    fun getPerformanceSeatBookInfoByPerformanceSeatIdAndToken(
        performanceSeatId: Long,
        token: String
    ): PerformanceSeatBookInfo? {
        return performanceSeatBookInfoRepository.findByPerformanceSeatIdAndToken(performanceSeatId, token)
    }
}
