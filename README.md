# COMMERCE back-end

## Description
일반적인 커머스의 백엔드 Application의 샘플을 제공합니다.

----

### Technology Stacks
1. Java11
2. Spring Boot 2.6.12
3. Gradle
4. JPA
5. H2

---

### Requirements

- 회원
  - 회원은 아이디, 비밀번호, 이름, 이메일, 비밀번호, 휴대전화번호, 주소를 가진다.
- 상품
  - 상품은 이름, 가격, 이미지 경로, 할인가격, 할인방식, 재고, 매입가를 가진다.
  - 각 상품의 할인은 정률 할인 혹은 정액 할인, 할인없음을 가질 수 있다.
  - 재고가 5개 이하인 상품은 전시, 장바구니에서 품절 표시가 되어야 한다.
  - 상품은 각 회원별로 장바구니에 담을 수 있다.
  - 각 상품은 이름, 가격, 이미지 경로, 할인가격, 할인방식, 전시상태(대기, 전시, 품절, 종료)를 가진다.
- 주문
  - 주문은 주문번호, 주문자, 주문상태(주문완료, 주문취소), 주문일자, 총 가격을 가진다.
  - 주문상세는 주문번호, 전시정보를 가진다.
  - 배송이 시작되면 주문취소를 할 수 없다.
  - 여러 상품을 한번에 주문할 수 있다.
- 배송
  - 배송은 배송번호, 배송상태(대기, 배송중, 배송완료, 배송취소), 배송지, 배송비를 가진다.
- 기타
  - 모든 데이터는 생성일을 가져야한다.
  - 수정이 가능한 데이터는 수정일을 가져야한다.
  - 삭제가 가능한 데이터는 활성화여부 컬럼을 가져야한다.

---

### Functional Requirements

- 회원기능
  - 회원가입
- 상품기능
  - 상품목록 조회
  - 상품상세 조회
  - 장바구니 담기
- 장바구니기능
  - 장바구니 조회
  - 장바구니 담기
  - 장바구니 삭제
- 주문기능
  - 주문하기
  - 주문취소
  - 주문목록 조회(배송상태 포함)

---

### ERD
[ERD 링크](https://dbdiagram.io/d/6342ae8ff0018a1c5fc4fdc6){:target="_blank"}
