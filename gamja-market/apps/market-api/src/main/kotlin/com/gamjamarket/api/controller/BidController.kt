package com.gamjamarket.api.controller

import com.gamjamarket.api.dto.request.BidRequest
import com.gamjamarket.api.service.BidService
import com.gamjamarket.utils.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/bids")
class BidController(
    private val bidService: BidService
) {

    @PostMapping("/{auctionId}/bids")
    fun placeBid(
        @PathVariable auctionId: Long,
        @RequestBody request: BidRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        bidService.placeBid(
            auctionId = auctionId,
            bidderId = request.bidderId,
            bidPrice = request.bidPrice
        )

        return ResponseEntity.ok(ApiResponse.successWithNoData("성공적으로 입찰되었습니다."))
    }
}