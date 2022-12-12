# 키친포스

## 요구 사항
### 상품(product)
```text
# 상품 생성
POST /api/products

# 상품 목록 조회
GET /api/products
```
* [x] 상품을 생성할 수 있다.
  * [x] 상품의 가격은 0원 이상이어야 한다.
* [x] 상품의 목록을 조회할 수 있다.

### 메뉴(menu)
```text
# 메뉴 생성
POST /api/menus

# 메뉴 목록 조회
GET /api/menus
```
* [x] 메뉴를 생성할 수 있다.
  * [x] 메뉴의 가격은 0원 이상이어야 한다.
  * [x] 메뉴는 등록된 메뉴 그룹에 속해야 한다.
  * [x] 메뉴 상품은 모두 등록된 상품이어야 한다.
  * [x] 메뉴의 가격은 메뉴 상품들의 가격의 합보다 클 수 없다.
* [x] 메뉴 목록을 조회할 수 있다.

### 메뉴 그룹(menu group)
```text
# 메뉴 그룹 생성
POST /api/menu-groups

# 메뉴 그룹 목록 조회
GET /api/menu-groups
```
* [x] 메뉴 그룹을 생성할 수 있다.
* [x] 메뉴 그룹 목록을 조회할 수 있다.

### 주문 테이블(table)
```text
# 주문 테이블 생성
POST /api/tables

# 주문 테이블 목록 조회
GET /api/tables

# 주문 테이블이 비어있는지 여부 변경
PUT /api/tables/{orderTableId}/empty

# 주문 테이블에 방문한 손님 수 변경
PUT /api/tables/{orderTableId]/number-of-guests
```
* [x] 주문 테이블을 생성할 수 있다.
* [x] 주문 테이블 목록을 조회할 수 있다.
* [x] 주문 테이블이 비어있는지 여부를 변경할 수 있다.
  * [x] 주문 테이블은 등록되어 있어야 한다.
  * [x] 주문 테이블은 단체 지정이 되어 있지 않아야 한다.
  * [x] 주문 테이블의 주문 상태는 조리 중이거나 식사 중이면 안된다.
* [x] 주문 테이블에 방문한 손님 수를 변경할 수 있다.
  * [x] 주문 테이블의 방문한 손님 수가 0명 이상이어야 한다.
  * [x] 주문 테이블은 등록되어 있어야 한다.
  * [x] 주문 테이블은 빈 테이블이 아니어야 한다.

### 단체 지정(table group)
```text
# 단체 지정 등록
POST /api/table-groups

# 단체 지정 해제
GET /api/table-groups/{tableGroupId}
```
* [x] 단체 지정을 할 수 있다.
  * [x] 주문 테이블들이 2개 이상 있어야 한다.
  * [x] 주문 테이블들은 모두 등록된 주문 테이블이어야 한다.
  * [x] 주문 테이블들은 빈 테이블이어야 한다.
  * [x] 이미 단체 지정된 주문 테이블은 단체 지정을 할 수 없다.
* [x] 단체 지정을 해제할 수 있다.
  * [x] 단체 내 주문 테이블들의 상태는 조리 중이거나 식사중이면 안된다.
  
### 주문(order)
```text
# 주문 생성
POST /api/orders

# 주문 목록 조회
GET /api/orders

# 주문 상태 변경
PUT /api/orders/{orderId}/order-status
```
* [x] 주문을 생성할 수 있다.
  * [x] 주문 항목은 최소 1개 이상 있어야 한다.
  * [x] 주문 항목 속 메뉴들은 모두 등록된 메뉴여야 한다.
  * [x] 주문 테이블은 등록된 테이블이어야 한다.
  * [x] 주문 테이블은 빈 테이블이 아니어야 한다.
* [x] 주문 목록을 조회할 수 있다.
* [x] 주문 상태를 변경할 수 있다.
  * [x] 주문은 기등록된 주문이어야 한다.
  * [x] 주문 상태가 완료가 아니어야 한다.
  
## 용어 사전

| 한글명      | 영문명              | 설명                            |
|----------|------------------|-------------------------------|
| 상품       | product          | 메뉴를 관리하는 기준이 되는 데이터           |
| 메뉴 그룹    | menu group       | 메뉴 묶음, 분류                     |
| 메뉴       | menu             | 메뉴 그룹에 속하는 실제 주문 가능 단위        |
| 메뉴 상품    | menu product     | 메뉴에 속하는 수량이 있는 상품             |
| 금액       | amount           | 가격 * 수량                       |
| 주문 테이블   | order table      | 매장에서 주문이 발생하는 영역              |
| 빈 테이블    | empty table      | 주문을 등록할 수 없는 주문 테이블           |
| 주문       | order            | 매장에서 발생하는 주문                  |
| 주문 상태    | order status     | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정    | table group      | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목    | order line item  | 주문에 속하는 수량이 있는 메뉴             |
| 매장 식사    | eat in           | 포장하지 않고 매장에서 식사하는 것           |
| 조리 중     | COOKING          | 조리 중                          |
| 식사 중     | MEAL             | 식사 중                          |
| 완료       | COMPLETION       | 완료                            |
| 주문메뉴     | order menu       | 주문한 메뉴 |

## 1단계 - 테스트를 통한 코드 보호
### 요구 사항
- [x] 키친포스의 요구사항을 작성한다.
- [x] 정리한 요구사항을 토대로 테스트 코드 작성 ➝ 모든 Business Object에 대한 테스트 코드 작성
  - @SpringBootTest를 이용한 통합 테스트 또는 @ExtendWith(MockitoExtension.class)를 이용한 단위 테스트 코드 작성
    - [x] @ExtendWith(MockitoExtension.class)를 이용한 Business Object 테스트 코드 작성
  - [x] 인수 테스트 코드 작성(권장)
- [x] lombok 사용하지 않을 것

### 힌트
- `http` 디렉토리의 `.http` 파일을 보고 어떤 요청을 받는지 참고할 수 있다. 
- `src/main/resources/db/migration` 디렉토리의 `.sql` 파일을 보고 어떤 관계로 테이블이 생성되어 있는지 참고할 수 있다.
- 키친포스 요구사항 작성 시, 하기 예제를 참고한다.
```text
### 상품

* 상품을 등록할 수 있다.
* 상품의 가격이 올바르지 않으면 등록할 수 없다.
    * 상품의 가격은 0 원 이상이어야 한다.
* 상품의 목록을 조회할 수 있다.
```

## 2단계 - 서비스 리팩터링
### 요구 사항
단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드를 분리해 단위 테스트 가능한 코드에 대해 단위 테스트 구현
- [x] JPA를 사용한다면, `spring.jpa.hibernate.ddl-auto=validate` 옵션 설정
- 데이터베이스 스키마 변경 및 마이그레이션 필요 시, 아래 문서 활용
  - flyway 활용

#### 프로그래밍 요구사항
- [x] lombok 사용하지 않을 것
- [x] 자바 코드 컨벤션 지키기
- [x] indent 1까지 허용
- [x] 3항 연산자 및 else, switch/case 사용 금지
- [x] 모든 기능은 TDD로 구현 해야 하며 단위 테스트 필수
  - 핵심 로직을 구현하는 코드와 UI 담당하는 로직을 구분 해 UI 로직은 테스트 제외
- [x] 함수 (또는 메소드) 길이가 10라인 넘지 않도록 구현
- [x] 배열 대신 컬렉션 사용
- [x] 모든 원시 값과 문자열을 포장 할 것
  - [x] Name
  - [x] Price
  - [x] Quantity
  - [x] NumberOfGuests
  - [x] empty
- [x] 축약 금지
- [x] 일급 컬렉션 사용
- [x] 엔티티 작게 유지
- [ ] 인스턴스 변수가 3개 이상인 클래스 사용하지 않음

## 3단계 - 의존성 리팩터링
### 요구사항
- [x] 의존성 관점에서 설계 검토
  - [x] 메뉴의 이름과 가격이 변경되면 주문 항목도 함께 변경된다. 메뉴 정보가 변경되더라도 주문 항목이 변경되지 않게 구현
    - Order에 저장하는 menu를 OrderMenu로 변경하고, menuId, menuName, menuPrice 저장 
  - 클래스 간의 방향도 중요하고 패키지 간의 방향도 중요하다. 클래스 사이, 패키지 사이 의존 관계는 단방향이 되어야 함

#### 프로그래밍 요구사항
- lombok 사용하지 않을 것

### 힌트
- 함께 생성되고 함께 삭제되는 객체들을 묶어라
- 불변식을 지켜야 하는 객체들을 묶어라
- 가능하면 분리하라
- 연관 관계는 다양하게 구현 가능
  - 직접 참조(객체 참조를 이용한 연관 관계)
  - 간접 참조(리포지토리를 통한 탐색)

### 추가 작업 필요 사항
- [x] final 붙인 변수들 체크
- [x] 테스트를 위한 식별자 포함 생성자 팩토리 어떻게 할지 고민
