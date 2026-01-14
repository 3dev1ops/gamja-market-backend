# Gamja Market Backend (감자마켓 백엔드)

> **High-concurrency Auction System: Handling real-time bids with WebSocket & Redis.**  
> 실시간 동시성 제어 경매 시스템: WebSocket과 Redis로 구현한 끊김 없는 입찰 경험.

## 1. 프로젝트 소개 (Overview)
감자마켓(Gamja Market)은 지역 기반의 중고 물품 **경매** 플랫폼입니다.
본 리포지토리는 감자마켓의 **백엔드 서버** 코드를 담고 있습니다.

## 2. 기술 스택 (Tech Stack)

| Category | Technology | Description |
| --- | --- | --- |
| **Language** | Kotlin | 정적 타입의 모던 JVM 언어 |
| **Framework** | Spring Boot | 엔터프라이즈급 백엔드 프레임워크 |
| **Database** | PostgreSQL | 메인 관계형 데이터베이스 |
| **Cache/Message** | Redis | 실시간 입찰 Pub/Sub 및 세션/캐싱 처리 |
| **Build Tool** | Gradle | 빌드 및 의존성 관리 |

## 3. 주요 기능 (Key Features)

*   **실시간 경매 (Live Auction)**: WebSocket을 이용한 실시간 호가 및 입찰 중계.
*   **동시성 제어 (Concurrency Control)**: Redis 분산 락(Distributed Lock) 등을 활용한 입찰 충돌 방지.
*   **상품 관리**: 경매 물품 등록, 수정, 상태 관리.
*   **사용자 관리**: 회원가입, 로그인 (Auth), 마이페이지.

## 4. 시작하기 (Getting Started)

### 의존성 설치 및 실행
```bash
./gradlew bootRun
```

*(추후 상세 실행 가이드 업데이트 예정)*

## 5. 문서 (Documentation)
전체 시스템 아키텍처 및 API 명세는 [gamja-docs-infra](https://github.com/3dev1ops/gamja-docs-infra) 리포지토리에서 관리합니다.
