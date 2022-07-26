# Spring-boot 첫주 과제
---

## Use Case Diagram
![usercase](https://user-images.githubusercontent.com/79980357/180920998-153c22d1-5e44-47ff-9002-80e27aebd7ca.PNG)

## API 명세서

---

![image](https://user-images.githubusercontent.com/79980357/180927070-94410788-f776-48c3-81fc-26247509a473.png)

## 과제질문 및 답변

---
Q. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)<br>

A.<BR> 1. 수정 - Put method 사용하였고,url 경로에 해당하는 게시글 id,  requestBody에 수정될 내용을 Json으로 데이터 전송하였습니다.
그 후에 repository를 사용하여 해당하는 내용을 찾고, requestBody에 있는 내용으로 교환하였습니다. 
<BR>2. 삭제 - 수정과 마찬가지로 URL 경로에 해당하는 게시글 id, requestBody에 해당하는 비밀번호를 보내도록 하였고, repository를 통해서
해당하는 게시글 비밀번호와, body에 포함된 비밀번호를 확인하여서 같을 경우 삭제 진행하였습니다.


Q. 어떤 상황에 어떤 방식의 request를 써야하나요?<br>

A. 우선 REST API 정의를 정리하자면, REST (Representational State Transfer) 약자이며 <br>
자원(Resource) :URI<br>
행위(Verb): HTTP Method<br>
표현(Representational) 로 이루어져 있다.
<br> 

HTTP Method 에 맞춰서 request를 쓰는것이 지금 생각하기로는 맞는거 같다.
추가적인 규칙은 따로 만들어보면서, 경우에 맞춰하면 될것 같다.
![image](https://user-images.githubusercontent.com/79980357/180946300-66215111-9759-4cb4-b598-09604c5cebe7.png)

Q. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?<br>

A.  

괜찮은 점
1. 우선 HTTP Method를 상황에 맞춰서 잘 사용한거 같다.<br>
2. URL 도 REST 규칙에 맞게 작성한거 같다.

아쉬운점
1. Response가 조금 더 깔끔하게 만들지 못한게 아쉽다. (응답 JSON 형식)
2. 비밀번호 확인하는 Method는 어떤 것을 사용하는게 맞는지 여전히 잘 모르겠다.

Q. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)<br>

A.
네. package를 잘 나누어서 기능에 맞춰 사용하였습니다.

Q. 작성한 코드에서 빈(Bean)을 모두 찾아보세요!<br>

A. <br>
#### Bean 간단 개념
Spring Bean 이란 스프링 컨테이너에 의해 관리되는 자바 객체(POJO)를 의미한다.<BR>
Spring Contianer 란 스프링 빈의 생명 주기를 관리하며, 생성된 스프링 빈들에게 추가적인 기능을 제공하는 역할을 한다. IoC,DI 의  원리가 스프링 컨테이너에 적용된다.. (IoC,DI는 무엇인가)<BR>
개발자가 new 연산자, 인터페이스 호출, 팩토리 호출 방식으로 객체를 생성하고 소멸하지만, 스프링 컨테이너를 사용하면 해당 역할을 대신해 준다.
제어 흐름을 외부에서 관리하게 된다.

#### Bean을 찾아보자

컴포넌트 스캔 @Component를 붙은 class들은 전부 스프링 컨테이너에 등록된다.
@Controller, @Service, @Repository, @Configuration는 @Component의 상속을 받고 있으므로 모두 컴포넌트 스캔의 대상이다.
<br>

이렇게 봤을 때 내가 만든 프로젝트에서 DTO 클래스, 시간을 나타내기 위해서 사용한 TimeStamped interface를 제외하고 모두 
Spring container에 등록되는 Bean이다.