package com.gamjamarket.domain.enums

enum class CancelReason(val description: String) {
    ITEM_DAMAGED("상품 파손 및 분실"),
    PRICE_MISTAKE("등록 정보(가격 등) 입력 실수"),
    SIMPLE_CHANGE_OF_MIND("단순 변심") // 패널티 대상
}