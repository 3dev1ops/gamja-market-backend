package com.gamjamarket.api.service

import com.gamjamarket.api.dto.response.BidHistoryResponse
import com.gamjamarket.api.dto.response.BidResponse
import com.gamjamarket.domain.Bid
import com.gamjamarket.repository.AuctionRepository
import com.gamjamarket.repository.BidRepository
import com.gamjamarket.repository.UserRepository
import com.gamjamarket.utils.exception.BusinessException
import com.gamjamarket.utils.response.ResultCode
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class BidService(
    private val redisTemplate: RedisTemplate<String, String>,
    private val auctionRepository: AuctionRepository,
    private val bidRepository: BidRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun placeBid(auctionId: Long, bidderId: UUID, bidPrice: Long): BidResponse {
        val highestBidKey = "auction:$auctionId:highest_bid"

        val cachedHighestPriceStr = redisTemplate.opsForValue().get(highestBidKey)
        if (cachedHighestPriceStr != null) {
            val cachedHighestPrice = cachedHighestPriceStr.toLong()
            if (bidPrice <= cachedHighestPrice) {
                throw BusinessException(ResultCode.BID_LOWER_THAN_HIGHEST, "현재 최고 입찰가(${cachedHighestPrice}원) 보다 높은 금액을 제시해야 합니다.")
            }
        }

        val auction = auctionRepository.findByIdWithItemAndSellerForUpdate(auctionId)
            ?: throw BusinessException(ResultCode.AUCTION_NOT_FOUND)

        if (auction.item.seller.id == bidderId) {
            throw BusinessException(ResultCode.BID_OWN_ITEM)
        }

        val now = LocalDateTime.now()
        if (auction.endAt.isBefore(now)) {
            throw BusinessException(ResultCode.AUCTION_ALREADY_ENDED)
        }

        if (bidPrice < auction.startPrice) {
            throw BusinessException(ResultCode.BID_LOWER_THAN_START_PRICE, "입찰 금액은 시작가(${auction.startPrice}원) 이상이어야 합니다.")
        }

        val highestBid = bidRepository.findTopByAuctionIdOrderByBidPriceDesc(auctionId)
        val actualHighestPrice = highestBid?.bidPrice ?: auction.startPrice

        if (bidPrice <= actualHighestPrice) {
            throw BusinessException(ResultCode.BID_LOWER_THAN_HIGHEST, "현재 최고 입찰가(${actualHighestPrice}원)보다 높은 금액을 제시해야 합니다.")
        }

        val bidderProxy = userRepository.getReferenceById(bidderId)

        val  newBid = Bid(
            auction = auction,
            bidder = bidderProxy,
            bidPrice = bidPrice
        )

        bidRepository.save(newBid)

        redisTemplate.opsForValue().set(highestBidKey, bidPrice.toString())

        return BidResponse(
            currentHighestPrice = newBid.bidPrice,
            bidTime = newBid.createdAt ?: LocalDateTime.now() // BaseTimeEntity 활용
        )
    }

    @Transactional(readOnly = true)
    fun getBidHistory(auctionId: Long, pageable: Pageable): Page<BidHistoryResponse> {

        if (!auctionRepository.existsById(auctionId)) {
            throw BusinessException(ResultCode.AUCTION_NOT_FOUND)
        }

        val bidPage = bidRepository.findByAuctionIdWithBidder(auctionId, pageable)

        return bidPage.map { bid ->
            BidHistoryResponse(
                bidId = bid.id!!,
                bidderId = bid.bidder.id,
                bidderName = bid.bidder.nickname,
                bidPrice = bid.bidPrice,
                bidTime = bid.createdAt ?: LocalDateTime.now()
            )
        }
    }
}
