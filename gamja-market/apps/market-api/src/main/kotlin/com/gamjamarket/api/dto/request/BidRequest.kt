package com.gamjamarket.api.dto.request

import java.util.UUID

data class BidRequest(
    val bidderId: UUID,

    val bidPrice: Long
)
