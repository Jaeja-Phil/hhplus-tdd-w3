# 동시성 이슈 발생 포인트 & 전략

## 1. 좌석 선점

### 문제점

대기열 토큰을 가지고 있는 사용자들 중에서 같은 좌석을 선점하고자 하는 경우 동시성 이슈가 발생할 수 있음.

> ex. 아이유 콘서트 S좌석 1번은 인기가 많으며, 여러 사용자가 동시에 선점을 시도할 수 있음

### 해결 방법

#### 1. database row level lock

좌석에 대한 row를 lock하여 별도의 3rd party 툴을 사용하지 않고도 데이터베이스만으로도 동시성 이슈를 해결할 수 있음.
이때 사용하기 적절한 lock의 종류는 `pessimistic lock`이며, `select for update`를 사용하여 row level lock을 걸 수 있음.

optimistic lock은 사용하기 적절하다고 판단하지 않은 이유?
> db까지 도달 가능한 요청을 허용하는 최대 대기열 토큰 갯수를 몇개로 지정할지에 대한 요구사항은 언제든 변경될 수 있음
> scalable하게 만든 시스템이라면 한번에 적지 않은 동시접속자를 받아들일 수 있어야 함
> 이때, optimistic lock을 사용하게 되면 특정 인기 좌석에 대한 선점이 지나치게 과열될 수 있음
> 
> ex) 팬미팅 콘서트일 경우 1번에 가까운 자리일수록 인기가 많아 지속적으로 선점 시도가 발생하고, 모두 실패할 수 있음
> 대기열 토큰의 만료시간이 있다는 특성상 유효기간 안에 아예 선점이 불가능해지는 경우도 발생할 수 있으므로, 먼저 신청한 사용자에게 우선권을 주는 방식인
> pessimistic lock을 사용하는 것이 적절하다고 판단
> 
> Note: 특히나, 대기열 토큰이 유효하지 않은 사용자들의 요청은 db까지 찌르지 않게된다는 가정이라면, lock으로 인한 성능 저하는
> 현재 유효한 토큰을 가지고 있는 사용자들의 요청에 한하여 발생하므로, 성능 저하는 우려할만큼의 문제가 되지 않을 것으로 판단

#### 2. Redis lock

Redis를 사용하여 lock을 구현할 수 있음.
Redis는 메모리 기반의 key-value 저장소이며, 빠른 속도로 데이터를 저장하고 조회할 수 있음.
좌석 선점 요청을 처리하는 어플리케이션 서버에서 Redis 클라이언트를 이용하여 lock을 구현할 수 있음.

ex)
```kotlin
fun lockSeat(seatId: Long, token: String): Boolean {
    val key = "lock:$seatId"
    val value = token
    return redisTemplate.opsForValue().setIfAbsent(key, value)
}
```

특정 비지니스 요구사항 (이 경우, 좌석 선점) 에 해당하는 키 명을 사용하여, 이미 해당 키가 존재하는지 확인 후 없다면 해당 키를 생성하고 값을 저장하는 
방식으로 lock을 구현할 수 있음.

> 주의할 점:
> - lock을 구현할 때, lock을 획득한 후에는 반드시 lock을 해제해야 함

위 주의사항을 해결하기 위해 아래와 같은 예제를 사용해 볼 수 있음:

```kotlin
fun lockSeat(seatId: Long, token: String): Boolean {
    val key = "lock:$seatId"
    val value = token
    val expireTime = 10L // 만료시간을 10초로 가정
    return redisTemplate.execute { connection ->
        val result = 
            connection.set(
              key.toByteArray(),
              value.toByteArray(),
              Expiration.seconds(expireTime),
              SetOption.SET_IF_ABSENT
            )
        result
    } ?: false
}
```

#### 3. Kafka

Kafka를 사용하여 이벤트 기반으로 처리할 수 있음.
좌석 선점 요청을 받은 후, 해당 요청을 Kafka에 전달하고, Kafka Consumer에서 해당 요청을 처리하는 방식으로 동시성 이슈를 해결할 수 있음.
이 경우, UI까지 고려하였을 때 처리할 수 있는 방식은 두가지가 있음:
1. 실제로 좌석 선점 요청이 성공/실패했는지에 대한 정보를 UI에 보여주는 방식
2. 사용자에게 좌석 선점 요청이 완료되었다는 메시지를 보여주는 것이 아닌, 좌석 선점 요청이 성공적으로 처리되었음을 알려주는 메시지를 보여주는 방식

1번의 경우, 동기적으로 처리하지 않는다면 사용자가 좌석 선점 요청을 했을 때, 성공/실패 여부를 알 수 없음.
이럴 경우, 느리지만 transactional한 방식으로 처리할 수 있음.
- producer가 이벤트를 전송하고, consumer가 해당 이벤트를 처리하면서 성공/실패 여부를 producer에게 알려주는 방식

kafka가 비동기 처리를 가정하고 사용하는 툴임을 감안하였을 때, 2번의 방식이 더 적합하다고 판단됨.

### 선택한 방법

Redis lock을 사용하여 동시성 이슈를 해결하는 것이 가장 적합하다고 판단하였음

근거 1: 데이터베이스의 row level lock을 사용하는 제약사항 추가 방식은 바람직하지 않다고 판단
- 데이터베이스의 row level lock을 사용하게 되면, 해당 row에 대한 lock을 획득하기 위해 데이터베이스에 부하를 줄 수 있음
- 특정 테이블에서만 필요한 lock 때문에 lock 사용을 허용하는 것이 팀의 컨벤션에 적합하지 않을 수 있음
  - ex) lock을 사용하지 않을 시 구현이 불가능한 경우가 아니라면, lock을 사용하지 않고 개발하여 고민 포인트를 덜어주는 것이 개발팀에게 더 이득이
    되는 상황이 많지 않을까 라는 생각... (db lock까지 항상 같이 고민하여 개발하는 것은 사람의 생각 프로세스에 레이어를 하나 더 추가해두어야 하는 느낌임)

근거 2: redis를 이용한 구현은 비교적 쉽다
- redis를 사용하여 lock을 구현하는 어렵지 않게 구현할 수 있음
- 데이터베이스에 부하를 주지 않고도 동시성 이슈를 해결할 수 있음
  - 데이터베이스가 point of failure가 되는 것을 방지해야만 함

> **redis를 사용하기 위한 추가적인 비용이 발생할 수 있음은 인지하자**

근거 3: kafka를 사용하는 방법은 비동기적인 처리를 가정하고 있음
- event driven 방식의 설계가 준비되어 있지 않은 어플리케이션이라면 kafka가 적합하지 않을 수 있음
  - 높은 구현/설계 난이도 대비 실질적인 output이 적을 수 있음

---

## 0-1. 브랜치 전략

- `main`: 운영 브랜치
- `develop`: 개발 브랜치
  - 사내 테스트 서버용 브랜치로도 사용 
  - `feature/{feature-name}`: 기능 개발 브랜치, develop에서 분기
- `release/{version}`: 배포 전 브랜치, develop에서 분기

## 0-2. Lint

- ktlint 적용
- `./gradlew ktlintCheck`로 코드 스타일 확인

> ktlint를 PR시 적용하려고 했으나 수정해야 할 스타일이 너무 많아서 PR시 적용하지 않음
> 추후 일관된 스타일을 추출하고 한번에 chore로 적용

---

## 1. ERD

![ERD](static/ERD.png)

### dbdiagram.io
```sql
Table users as U {
  id uuid [pk]
  balance decimal [not null, default: 0] 
}

enum token_statuses {
  IN_QUEUE
  IN_PROGRESS
  EXPIRED
}

Table user_queue_tokens as UQT {
  id bigint [pk]
  user_id uuid [ref: > U.id]
  token varchar [not null, unique, note: '유저의 id와 생성 시간을 합친 값을 MD5하여 만듬']
  status token_statuses [not null, default: `token_statuses.IN_QUEUE`]
}

Table concerts as C {
  id bigint [pk]
  name varchar [not null]
}

Table concert_performances as CP {
  id bigint [pk]
  concert_id bigint [ref: > C.id]
  performance_datetime datetime [not null, default: `now()`]
  max_seats int [not null, default: 0]
}

Table performance_seats as PS {
  id bigint [pk]
  concert_performance_id bigint [ref: > CP.id]
  seat_number int [not null, default: 0]
  user_id uuid [null, ref: > U.id, note: '좌석을 예약 성공한 유저']
  booked boolean [not null, default: false]
}

Table performance_seat_book_info as PSBI {
  id bigint [pk]
  performance_seat_id bigint [ref: - PS.id]
  token varchar [null, note: 'performance_seat 생성후 시도된 좌석 예약이 없을 경우 null 가능']
  book_attempt_at datetime [null, note: '좌석 예약 대기를 걸어둔 시간']
  book_success_at datetime [null, note: '좌석 예약에 성공한 시간']
}

Table performance_seat_book_logs as PSBL {
  id bigint [pk]
  performance_seat_id bigint [ref: > PS.id]
  token varchar [not null, note: '예약 시도를 위해 사용한 대기열 토큰']
  created_at datetime [not null, default: `now()`]
  updated_at datetime [not null, default: `now()`]
}
```

## 2. Sequence Diagram

### 대기열 토큰 발급

![create-user-token-sequence-diagram](static/create-user-token-sequence-diagram.png "대기열 토큰 발급")

### 좌석 예약 요청

![seat-booking-flow-sequence-diagram](static/seat-booking-flow-sequence-diagram.png "좌석 예약 요청")


## 3. API 명세서

### 토큰 없이 사용 가능 API 목록

#### 대기열 토큰 발급: POST /user_queue_tokens
- Request
  - user_id: UUID
  - Content-Type: application/json
  - Body: 
    ```json
    {
      "user_id": "UUID"
    }
    ```
- Response
  - 201 Created
    - Content-Type: application/json
    - Body:
      ```
      {
        "token": "TOKEN"
      }
      ```
  - 400 Bad Request
    - Content-Type: application/json
    - Body:
      ```
      {
        "message": "User not found"
      }
      ```
    - explanation
      - `user_id`에 해당하는 사용자가 존재하지 않음
- Note
  - 이후 사용되는 API 호출 시, `token`을 헤더에 `X-Reservation-Token`으로 전달해야 함

#### 유저 잔액 충전: POST /users/{user_id}/charge
- Request
  - user_id: UUID
  - amount: Decimal
  - Content-Type: application/json
  - Header: X-Reservation-Token: {token}
  - Body:
    ```json
    {
      "amount": 100.0
    }
    ```
- Response
  - 200 OK
    - Content-Type: application/json
    - Body:
      ```
      {
        "id": "UUID",
        "balance": 100.0
      }
      ```
  - 404 Not Found
    - Content-Type: application/json
    - Body:
      ```
      {
        "message": "User not found"
      }
      ```
    - explanation
      - `user_id`에 해당하는 사용자가 존재하지 않음

#### 유저 정보 조회 (잔액 조회, 예약 성공한 좌석 조회시 사용): GET /users/{user_id}
- Request
  - user_id: UUID
  - Content-Type: application/json
  - Header: X-Reservation-Token: {token}
  - Body: None
- Response
  - 200 OK
    - Content-Type: application/json
    - Body:
      ```
      {
        "id": "UUID",
        "balance": 100.0,
        "booked_performance_seats": [
          {
            "id": 1,
            "seat_number": 1,
            "book_success_at": "2024-04-01T00:00:00"
            "concert_performance": {
              "id": 1,
              "concert": {
                "id": 1,
                "name": "콘서트명"
              },
              "performance_datetime": "2024-04-01T00:00:00"
            }
          }
        ]
      }
      ```
  - 404 Not Found
    - Content-Type: application/json
    - Body:
      ```
      {
        "message": "User not found"
      }
      ```
    - explanation
      - `user_id`에 해당하는 사용자가 존재하지 않음

---

### 토큰이 필요한 API 목록

#### 공통 response
- 401 Unauthorized
  - Content-Type: application/json
  - Body:
    ```
    {
      "message": "Token is invalid"
    }
    ```
  - explanation
    - `token`이 유효하지 않음 

#### 대기열 토큰으로 정보 조회: GET /user_queue_tokens/token_info
- Request
  - token: String
  - Content-Type: application/json
  - Header: X-Reservation-Token: {token}
  - Body: None
- Response
  - 200 OK
    - Content-Type: application/json
    - Body:
      ```
      {
        "user_id": "UUID",
        "status": "one of (IN_QUEUE, IN_PROGRESS, EXPIRED)",
        "performance_seat_book_info": { // nullable
          "id": 1,
          "performance_seat_id": 1,
          "book_attempt_at": "2024-04-01T00:00:00",
          "book_success_at": null
        }
      }
      ```

#### 콘서트 목록 조회: GET /concerts
- Request
  - Content-Type: application/json
  - Header: X-Reservation-Token: {token}
  - Body: None
- Response
  - 200 OK
    - Content-Type: application/json
    - Body:
      ```
      [
        {
          "id": 1,
          "name": "콘서트명"
        }
      ]
      ```

#### 콘서트 예약 가능 날짜 조회: GET /concert_performances?concert_id={concert_id}
- Request
  - concert_id: Long
  - Content-Type: application/json
  - Header: X-Reservation-Token: {token}
  - Body: None
- Response
  - 200 OK
    - Content-Type: application/json
    - Body:
      ```
      [
        {
          "id": 1,
          "concert_id": 1,
          "max_seats": 50,
          "performance_datetime": "2024-04-01T00:00:00"
        }
      ]
      ```
  - 404 Not Found
    - Content-Type: application/json
    - Body:
      ```
      {
        "message": "Concert not found"
      }
      ```
    - explanation
      - `concert_id`에 해당하는 콘서트가 존재하지 않음
- Note
  - `max_seats`는 해당 콘서트 날짜에 대한 총 좌석 수
  - `performance_datetime`은 콘서트 날짜 및 시간
  - 빈 목록일 경우 해당 콘서트에 대한 예약이 모두 완료되었거나 예약이 불가능한(예: 공연 시작 시간이 지난 경우) 상태

#### 예약 가능한 좌석 조회: GET /performance_seats?concert_date_id={concert_date_id}
- Request
  - concert_date_id: Long
  - Content-Type: application/json
  - Header: X-Reservation-Token: {token}
  - Body: None
- Response
  - 200 OK
    - Content-Type: application/json
    - Body:
      ```
      [
        {"id": 1, "concert_performance_id": 1, "seat_number": 1, "booked": false},
        {"id": 2, "concert_performance_id": 1, "seat_number": 2, "booked": true}
      ]
      ```
  - 404 Not Found
    - Content-Type: application/json
    - Body:
      ```
      {
        "message": "Concert date not found"
      }
      ```
    - explanation
      - `concert_date_id`에 해당하는 콘서트 날짜가 존재하지 않음

#### 좌석 예약 요청: POST /performance_seats/{id}/book
- Request
  - id: Long
  - Content-Type: application/json
  - Header: X-Reservation-Token: {token}
  - Body: None
- Response
  - 201 Created
    - Content-Type: application/json
    - Body:
      ```
      {
        "performance_seat_id": 1,
        "book_attempt_at": "2024-04-01T00:00:00" // 예약 시도 시간
      }
      ```
  - 400 Bad Request
    - Content-Type: application/json
    - Body:
      ```
      {
        "message": "Seat is already booked"
      }
      ```
    - explanation
      - 해당 좌석이 이미 예약 완료되었음
  - 404 Not Found
    - Content-Type: application/json
    - Body:
      ```
      {
        "message": "Performance seat not found"
      }
      ```
    - explanation
      - `id`에 해당하는 좌석이 존재하지 않음 
  - 409 Conflict
    - Content-Type: application/json
    - Body:
      ```
      {
        "message": "User already has a reservation"
      }
      ```
    - explanation
      - `token`에 해당하는 사용자가 이미 예약중인 좌석이 있음
  - 409 Conflict
    - Content-Type: application/json
    - Body:
      ```
      {
         "message": "Other user is booking the seat"
      }
      ```
    - explanation
      - 해당 `id`에 해당하는 좌석을 예약중인 사용자가 있음 

#### 콘서트 예약 결제 요청: POST /performance_seats/{id}/pay
- Request
  - id: Long
  - Content-Type: application/json
  - Header: X-Reservation-Token: {token}
  - Body: None
- Response
  - 200 OK
    - Content-Type: application/json
    - Body:
      ```
      {
        "id": 1,
        "concert_performance_id": 1,
        "seat_number": 1,
        "user_id": "UUID",
        "booked": true
      }
      ```
  - 400 Bad Request
    - Content-Type: application/json
    - Body:
      ```
      {
        "message": "Queue expired"
      }
      ```
    - explanation
      - N분의 예약시간을 초과하여 예약이 불가능함
  - 403 Bad Request
    - Content-Type: application/json
    - Body:
      ```
      {
        "message": "Insufficient balance"
      }
      ```
    - explanation
      - 사용자의 잔액이 부족하여 결제가 불가능함
  - 404 Not Found
    - Content-Type: application/json
    - Body:
      ```
      {
        "message": "Performance seat not found"
      }
      ```
    - explanation
      - `id`에 해당하는 좌석이 존재하지 않음

---

# 과제 회고

### 1. 과제를 진행하면서 가장 어려웠던 점

DDD 개념을 생각하며 설계를 진행하면서 나름의 깔끔하다고 생각했던 설계에 허점을 발견하게 되었습니다 (양방향 관계를 가진 entity일 경우 도메인 VO로 변경할 시 무한루프 발생)
이를 위한 해답을 찾기 위해 DDD 방식으론 어떻게 접근해야 할까 고민해보는 시간을 가지게 되었고, 결론으론
1. 양방향 관계 지양
2. 한 도메인 묶음에 해당하는 aggregate 고려
를 찾아보게 되었습니다.

### 2. 설계 및 구현

TDD 방식으로 진행하며 생각보다 많은 시간이 든다는 것을 느끼게 되었습니다.
작업하며 안정감을 느낀 부분도 있었지만 `모든` 메서드에 대한 테스트케이스 작성이 과연 필요한가에 대한 의문을 가지게 되었고, 1liner 메서드는 테스트케이스는
작성을 하지 않는 방향을 고민해보는 것이 실용적이겠다고 생각하였습니다.

### 3. 기타

클린아키텍쳐에 대해 조금 더 탐구해보고 적용해볼 기회를 만들어봐야겠다고 생각하게 되었습니다.

