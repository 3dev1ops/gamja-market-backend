package com.gamjamarket.api.dto.response

import java.time.LocalDateTime

data class BidResponse(
    val currentHighestPrice: Long,
    val bidTime: LocalDateTime
)