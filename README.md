# random-item-draw-service

## Subject : 빠칭코 상품 뽑기 서비스
- Language : Java
- Runtime Version : Jdk 17.0.6
- 사용된 의존성 : Lombok, JUnit, assertj
- [jar 파일 다운로드 링크](https://drive.google.com/file/d/1qRR6yAvtF-43k6C9I0uypSL_0w5KDB1b/view?usp=sharing)

## Application 구조 :
![image](https://user-images.githubusercontent.com/96719735/243178749-e5c12b6d-1918-4b74-84ec-e880f15cf414.png)

## Application 의존 관계 :
- RandomBoxDrawApplication -> Service -> Repository -> Domain

## 테스트 케이스
![image](https://user-images.githubusercontent.com/96719735/243178794-347a34fa-636a-4af0-8996-db67da745d9f.png)

- 계층 별로 테스트 케이스 작성, 총 24개의 테스트 케이스

![image](https://github.com/2tsumo-hitori/random-item-draw-service/assets/96719735/a5509d13-f8d3-4d92-8075-06f2e8be5a58)

- 철저한 사전검증으로 프로그램 오류 발생 방지

## 설계의 주안점
- 스프링 프레임워크를 사용하지 않고, 최소한의 라이브러리를 활용해 순수한 자바 프로그램 구현

- 상위 계층만 하위 계층을 참조할 수 있게 구성

- SOLID 원칙에 의거한 프로그래밍
  - SRP : 도메인, 서비스, 레파지토리 계층 별로 한 클래스에서 하나의 책임을 가지게 끔 구현
  - OCP, DIP : DI를 직접 설계해서 각 클래스가 추상화에 의존하도록 구현
  - LSP : Item 추상 클래스를 상속받는 ItemGradeA, ItemGradeB 클래스 구현, 각각의 타입은 서로 호환됨
  - ISP : ItemDrawService 인터페이스와 해당 인터페이스를 support 하는 ItemDrawSupportInterface로 분리하여 구현
  
- Lombok 라이브러리의 val 타입을 사용해서 코드 가독성 향상 및 외부 수정에 안전하게 설계

- **탬플릿 콜백 패턴을 활용해 중복 로직 탬플릿화**
  <details>
    <summary>적용 전</summary>
  
  ```Java


	while(start) {
		System.out.println("뽑기 1번");
		System.out.println("금액 충전 2번");
		System.out.println("프로그램 종료 3번");

		switch (scanner.nextInt()) {
			case 1 :
				try {
					System.out.println("뽑기 횟수를 입력해주세요. 현재 잔액은 " + member.getMoney() + "원 입니다.");

					int count = scanner.nextInt();

					List<String> resultPrints = itemDrawService.draw(member, count, LocalDateTime.now());

					resultPrints.forEach(System.out::println);
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				try {
					System.out.println("충전 할 금액을 입력해주세요.");

					int chargeMoney = scanner.nextInt();

					memberRepository.chargeMoney(member, chargeMoney);

					System.out.println("충전 후 현재 금액은 " + member.getMoney() + "원 입니다.");
				} catch(IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				start = false;
		}
	}
    
  ```
  </details>
  
  <details>
    <summary>적용 후</summary>
  
  ```Java

	while(true) {
		System.out.println("뽑기 1번");
		System.out.println("금액 충전 2번");
		System.out.println("프로그램 종료 3번");

		switch (scanner.nextInt()) {
			case 1 :
				execute(drawAction);
				break;
			case 2:
				execute(chargeMoneyAction);
				break;
			case 3:
				return;
		}
	}
	
  ```
    </details>
	
   <details>
    <summary>2차 적용 전</summary>
  
  ```Java
   while (true) {
	    System.out.println("뽑기 1번");
	    System.out.println("금액 충전 2번");
	    System.out.println("프로그램 종료 3번");

	    try {
		switch (scanner.next()) {
		    case "1":
			execute(drawAction);
			break;
		    case "2":
			execute(chargeMoneyAction);
			break;
		    case "3":
			return;
		}
	    } catch (NumberFormatException e) {
		System.out.println("숫자를 입력해주세요.");
	    }
	}
   ```
	</details>
	
   <details>
    <summary>2차 적용 후</summary>
  
  ```Java
   while (start) {
		System.out.println("뽑기 1번");
		System.out.println("금액 충전 2번");
		System.out.println("프로그램 종료 3번");

		start = programExecution(drawAction, chargeMoneyAction);
	}
  	 ```
    </details>	
	   
		
- DIContainer 클래스에서 의존성 주입, 의존성 주입 컨테이너 직접 구현
   <details>
    <summary>펼치기/접기</summary>
  
  ```Java
  public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }

    public ItemDrawSupportService itemDrawSupportService() {
        return new RandomItemDrawSupportService(memberRepository(), itemRepository());
    }

    public ItemDrawService itemDrawService() {
        return new RandomItemDrawService(memberRepository(), itemDrawSupportService());
    }
  ```
    </details>

- 메서드 체이닝을 활용해 중복 코드 방지
   <details>
    <summary>펼치기/접기</summary>
  
  적용 전
  
  ```Java
  val notExpiredItems = luckyBoxDrawSupportService.getNotExpiredItems(startTime);

  notExpiredItems = luckyBoxDrawSupportService.sortItems; 
  ```
  
  적용 후
  
  ```Java
  val sortedNotExpiredItems = itemDrawSupportService
                .notExpiredItems(startTime)
                .sortItems();
  ```
</details>

- 상수를 활용해 하드코딩 방지 및 유지보수성 상승
  <details>
    <summary>펼치기/접기</summary>

    ```Java
    private static final int MIN_MONEY = 0;

    private static final int INITIAL_MONEY = 10000;

    private static final int DRAW_MONEY = 100;

    private static final int INITIAL_DRAW_COUNT_ON_ITEM_GRADE_B = 0;

    private static final int MAX_DRAW_COUNT_ON_ITEM_GRADE_B = 3;


    ```
  </details>
- 각 상품의 등급, 확률을 Enum 클래스를 활용해, 가독성과 타입 안정성을 제공
  <details>
    <summary>펼치기/접기</summary>

    ```Java
    public enum ItemGrade {
        A(90),
        B(10);

        protected int percentage;
        ItemGrade(int percentage) {
            this.percentage = percentage;
        }
        public int percentage() {
            return percentage;
        }

        public void setPercentage(int percentage) {
            this.percentage = percentage;
        }
    }

  ```
  </details>
  
- validate 메서드 구현, 발생할 수 있는 예외에 맞는 메세지를 적절하게 제공해 유지보수성 상승

  <details>
    <summary>펼치기/접기</summary>

    ```Java
    public class PreConditions {
        public static void validate(boolean expression, String message) {
            if(!expression) {
                throw new IllegalArgumentException(message);
            }
        }
    }
  ```
  
  ```Java
    public void chargeMoney(int money) {
        validate(money > MIN_MONEY, "값을 정확하게 입력해주세요.");

        this.money += money;
    }
  ```
  </details>
  
## 핵심 서비스 로직
```Java
@RequiredArgsConstructor
public class RandomItemDrawService implements ItemDrawService {

    private final MemberRepository memberRepository;

    private final ItemDrawSupportService itemDrawSupportService;

    private static final String FAILED_DRAW_MESSAGE = "꽝";

    @Override
    public List<String> draw(Member member, int count, LocalDateTime startTime) {
        memberRepository.resetDrawCount(member);

        val drawResults = new ArrayList<String>();

        val sortedNotExpiredItems = itemDrawSupportService
                .notExpiredItems(startTime)
                .sortItems();

        itemDrawSupportService.spendMoney(member, count);

        for (int i = 0; i < count; i ++) {

            boolean isDrawSuccess = false;

            for (ItemGrade grade : ItemGrade.values()) {
                val items = sortedNotExpiredItems.get(grade);

                val randomItem = items.get(new Random().nextInt(items.size()));

                if (itemDrawSupportService.isDrawSuccess(randomItem, member)) {
                    drawResults.add(itemDrawSupportService.acquireItem(member, randomItem));

                    isDrawSuccess = true;

                    break;
                }
            }
            if (!isDrawSuccess)
                drawResults.add(FAILED_DRAW_MESSAGE);
        }

        return drawResults;
    }
}
```

### 동작 설명
- 회원의 DrawCount를 초기값으로 변경
- 유통기한이 지난 상품을 필터링한 후에 TreeMap 자료구조를 사용해 정렬함.
- 이후 회원이 기입한 count 횟수만큼 반복
- A, B 순서대로 반복, 저장되어있는 상품 중 랜덤한 상품을 뽑음
- flag 변수를 사용해 A, B 상품 중 하나라도 뽑혔다면 변수 값을 true로 변경 후 break. 하나도 뽑히지 못했다면 꽝 메세지를 List에 추가
- 뽑기 후 결과 반환, 결과를 List에 추가
- 뽑기결과 문자열이 저장된 List 반환
