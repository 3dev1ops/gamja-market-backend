package com.gamjamarket.api.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class BidHistoryResponse(
    val bidId: Long,
    val bidderId: UUID,
    val bidderName: String,
    val bidPrice: Long,
    val bidTime: LocalDateTime,
)
