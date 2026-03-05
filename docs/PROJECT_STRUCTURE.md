# 프로젝트 구조

## 개요
감자마켓(Gamja Market)은 경매 기반 중고거래 플랫폼으로, Kotlin + Spring Boot 기반의 멀티 모듈 프로젝트입니다.

## 디렉토리 구조

```
gamja-market-backend/
├── apps/                    # 애플리케이션 모듈
│   ├── market-api/         # 메인 REST API 서버
│   ├── market-admin/       # 관리자 API 서버
│   └── market-batch/       # 배치/스케줄러 서버
│
├── libs/                    # 공통 라이브러리 모듈
│   ├── common-domain/      # 도메인 엔티티 및 Repository
│   ├── common-dto/         # 공통 DTO (비어있음)
│   └── common-utils/       # 공통 유틸리티 (예외, 응답 포맷)
│
├── docs/                    # 프로젝트 문서
├── gradle/                  # Gradle Wrapper 파일
├── build.gradle.kts        # 루트 빌드 설정
├── settings.gradle.kts     # 모듈 설정
└── gradlew                 # Gradle 실행 스크립트
```

## 모듈 설명

### 1. apps/market-api
**역할**: 사용자용 REST API 제공

**주요 기능**:
- 상품(Item) CRUD API
- 입찰(Bid) API
- 경매 관리
- Redis 기반 동시성 제어

**주요 컴포넌트**:
- `controller/`: REST API 엔드포인트
- `service/`: 비즈니스 로직
- `dto/`: 요청/응답 DTO
- `config/`: JPA, Redis 설정
- `exception/`: 전역 예외 처리

### 2. apps/market-admin
**역할**: 관리자용 API 제공 (개발 중)

**현재 상태**: 기본 애플리케이션 구조만 구현됨

### 3. apps/market-batch
**역할**: 배치 작업 및 스케줄러 실행

**주요 기능**:
- 경매 종료 처리 스케줄러
- 조회수 동기화 (Redis → DB)
- Spring Batch 작업

**주요 컴포넌트**:
- `scheduler/`: 주기적 작업 실행
- `config/`: Batch, JPA 설정

### 4. libs/common-domain
**역할**: 공통 도메인 엔티티 및 Repository

**주요 엔티티**:
- `Item`: 상품
- `Auction`: 경매
- `Bid`: 입찰
- `User`: 사용자
- `Category`: 카테고리
- `ItemImage`: 상품 이미지

**주요 Repository**:
- Spring Data JPA 기반 Repository 인터페이스

### 5. libs/common-utils
**역할**: 공통 유틸리티 및 공통 응답 포맷

**주요 컴포넌트**:
- `exception/BusinessException`: 비즈니스 예외 처리
- `response/ApiResponse`: 표준 API 응답 포맷
- `response/ResultCode`: 응답 코드 정의

## 기술 스택

- **언어**: Kotlin 1.9.25
- **프레임워크**: Spring Boot 3.4.1
- **데이터베이스**: MySQL (JPA/Hibernate)
- **캐시**: Redis
- **빌드 도구**: Gradle 8.14
- **배치**: Spring Batch

## 빌드 및 실행

### 전체 빌드
```bash
./gradlew clean build
```

### 테스트 제외 빌드
```bash
./gradlew clean build -x test
```

### 특정 모듈 실행
```bash
# API 서버
./gradlew :apps:market-api:bootRun

# 배치 서버
./gradlew :apps:market-batch:bootRun

# 관리자 서버
./gradlew :apps:market-admin:bootRun
```

## 환경 설정

각 애플리케이션 모듈은 `src/main/resources/application.yml` 파일이 필요합니다.
(`.gitignore`에 포함되어 있으므로 각자 로컬에서 생성 필요)

샘플 파일: `application.yml.sample` 참고

## 의존성 관계

```
market-api ─┐
market-admin ├─> common-domain ─> common-utils
market-batch ┘
```

모든 애플리케이션 모듈은 `common-domain`에 의존하며,
`common-domain`은 `common-utils`에 의존합니다.

## 주요 비즈니스 로직

### 경매 입찰 흐름
1. 사용자가 입찰 요청 (BidController)
2. Redis 분산 락 획득
3. 비관적 락으로 경매 조회
4. 입찰 검증 (최소 금액, 경매 상태 등)
5. 입찰 저장 및 경매 정보 업데이트
6. 락 해제

### 경매 종료 처리
1. 스케줄러가 주기적으로 실행 (AuctionScheduler)
2. 종료 시간이 지난 경매 조회
3. 최고가 낙찰자 결정
4. 경매 상태 업데이트

## 참고사항

- 프로젝트는 멀티 모듈 구조로 설계되어 모듈별 독립적인 배포 가능
- 공통 도메인을 분리하여 코드 중복 최소화
- Redis를 활용한 동시성 제어 및 캐싱 구현
