package com.gamjamarket.domain.enums

enum class AuctionStatus(val description: String) {
    BEFORE_START("경매 시작 전"),
    ON_GOING("경매 진행 중"),
    BID_COMPLETED("낙찰 완료"),
    END_WITHOUT_BID("유찰 종료"),
    CANCELLED("경매 취소")
}