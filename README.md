# random-item-draw-service

## Subject : 빠칭코 상품 뽑기 서비스
- Language : Java
- Runtime Version : 17.0.6

## 패키지 구조 :
![image](https://github.com/2tsumo-hitori/random-item-draw-service/assets/96719735/72d6e90d-e221-4eac-9e8b-1fd61f47efce)

## Application 의존 관계 :
- Main -> Service -> Repository -> Domain

## 테스트 케이스
![image](https://github.com/2tsumo-hitori/random-item-draw-service/assets/96719735/3fc63a09-20b7-4dfe-822d-dd0b1819fb37)

- 계층 별로 테스트 케이스 작성, 총 27개의 테스트 케이스

- 철저한 사전검증으로 프로그램 오류 발생 방지

## 설계의 주안점
- 스프링 프레임워크를 사용하지 않고, 최소한의 라이브러리를 활용해 순수한 자바 프로그램 구현

- 상위 계층만 하위 계층을 참조할 수 있게 구성

- SOLID 원칙에 의거한 프로그래밍
  - SRP : 도메인, 서비스, 레파지토리 계층 별로 한 클래스에서 하나의 책임을 가지게 끔 구현
  - OCP, DIP : DI를 직접 설계해서 각 클래스가 추상화에 의존하도록 구현
  - LSP : Item 추상 클래스를 상속받는 ItemGradeA, ItemGradeB 클래스 구현, 각각의 타입은 서로 호환됨
  - ISP : ItemDrawService 인터페이스와 해당 인터페이스를 support 하는 ItemDrawSupportInterface로 분리하여 구현
  
- Config 클래스에서 DI 직접 구현

- Lombok 라이브러리의 val 타입을 사용해서 코드 가독성 향상 및 외부 수정에 안전하게 설계

- 메서드 체이닝을 활용해 중복 코드 방지

- 상수를 활용해 하드코딩 방지 및 유지보수성 상승

- 각 상품의 등급, 확률을 Enum 클래스를 활용해, 가독성과 타입 안정성을 제공

- validate 메서드 구현, 발생할 수 있는 예외에 맞는 메세지를 적절하게 제공해 유지보수성 상승
