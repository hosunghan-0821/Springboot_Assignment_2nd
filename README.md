# Spring-boot 둘째주 과제

---

## 과제 요구 사항
1. 전체 게시글 목록 조회 API
    - 제목, 작성자명, 작성 날짜를 조회하기
    - 작성 날짜 기준으로 내림차순 정렬하기
    - AccessToken이 없어도 조회 가능하게 하기
2. 게시글 작성 API
    - AccessToken이 있고, 유효한 Token일 때(== 로그인 상태일 때)만 작성 가능하게 하기
    - 제목 작성 내용을 입력하기
3. 게시글 조회 API
    - 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기
    - AccessToken이 없어도 조회 가능하게 하기
4. 게시글 수정 API
    - AccessToken이 있고, 유효한 Token이면서 해당 게시글 작성자만 수정 가능하게 하기
    - 제목, 작성자명, 작성 내용을 수정되게 하기
5. 게시글 삭제 API
    - AccessToken이 있고, 유효한 Token이면서 해당 게시글 작성자만 삭제 가능하게 하기
    - 글과 댓글이 함께 삭제되게 하기

<aside>
✌️ **새로운 요구사항을 구현해 보세요!**

</aside>

1. 아래 요구사항에 맞는 API 명세서와 ERD 설계
   **ERD 설계 →** [https://www.erdcloud.com/](https://www.erdcloud.com/)

   **API 명세서 작성 툴 →** [https://learnote-dev.com/java/Spring-A-문서-작성하기/](https://learnote-dev.com/java/Spring-A-%EB%AC%B8%EC%84%9C-%EC%9E%91%EC%84%B1%ED%95%98%EA%B8%B0/)

2. 회원 가입 API
    - 닉네임, 비밀번호, 비밀번호 확인을 request에서 전달받기
    - 닉네임은 `최소 4자 이상, 12자 이하 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성하기
    - 비밀번호는 `최소 4자 이상이며, 32자 이하 알파벳 소문자(a~z), 숫자(0~9)` 로 구성하기
    - 비밀번호 확인은 비밀번호와 정확하게 일치하기
3. 로그인 API
    - 닉네임, 비밀번호를 request에서 전달받기
    - 로그인 버튼을 누른 경우 닉네임과 비밀번호가 데이터베이스에 등록됐는지 확인하기
    - 로그인 성공 시, JWT를 활용하여 AccessToken을 발급하고,
      발급한 AccessToken은 Header의 Access-Token에 담아서 반환하기
    - 로그인 성공 시, JWT를 활용하여 RefreshToken을 발급하고,
      발급한 RefreshToken은 Header의 Refresh-Token에 담아서 반환하기

4. 로그인 검사
    - `아래 API를 제외하고` 모두 AccessToken, RefreshToken을 전달한 경우만 정상 response를 전달받을 수 있도록 하기
        - 회원가입 API
        - 로그인 API
        - 게시글 목록 조회 API
        - 게시글 조회 API
        - 댓글 목록 조회 API
    - cf. Authorization에 담긴 AccessToken으로 사용자 판단
5.  댓글 목록 조회 API
    - AccessToken이 없어도 댓글 목록 조회가 가능하도록 하기
    - 조회하는 게시글에 작성된 모든 댓글을 response에 포함하기
6. 댓글 작성 API
    - AccessToken이 있고, 유효한 Token일 때만 댓글 작성이 가능하도록 하기
7. 댓글 수정 API
    - AccessToken이 있고, 유효한 Token이면서 해당 사용자가 작성한 댓글만 수정 가능하도록 하기
8. 댓글 삭제 API
    - AccessToken이 있고, 유효한 Token이면서 해당  사용자가 작성한 댓글만 삭제 가능하도록 하기
9. 예외 처리
    - Refresh Token을 전달하지 않거나 정상 토큰이 아닐 때는 "Token이 유효하지 않습니다." 라는 에러 메세지를 response에 포함하기
    - 데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지를 response에 포함하기
    - 비밀번호와 비밀번호 확인 값이 일치하지 않을 때 “비밀번호와 비밀번호 확인이 일치하지 않습니다.” 라는 에러 메세지를 resonse에 포함하기
    - 로그인 시, 전달된 닉네임과 비밀번호 중 맞지 않는 정보가 있다면 "사용자를 찾을 수 없습니다."라는 에러 메세지를 response에 포함하기
    - AccessToken이 있고, 유효한 Token이면서 해당 사용자가 작성한 게시글/댓글이 아닌 경우에는 “작성자만 삭제할 수 있습니다.”라는 에러 메세지를 response에 포함하기

---

## 과제질문 및 답변

---
Q1. <br>FormLogin을 활용할 때와 활용하지 않을 때 어떤 차이점이 있었나요? <BR>

A1.<br> 
Security의 FormLogin 대신 UsernamePasswordAuthenticationFilter 상속받아  커스텀한 
loginFilter를 사용했습니다. 기존 로그인 폼을 이용하였을 때, jwt발급 같이 로직을 수행하기 어려움이 있었는데,
custom한 필터를 사용해서 로그인에 사용하였을 때, 조금 더 원하는데로 개발을 진행할 수 있었습니다.


Q2. <br>처음 설계한 API와 ERD에 변경사항이 있었나요?  변경되었다면 어떤 점 때문일까요? 첫 설계의 중요성에 대해 생각해보세요.

A2.<br>
네 있었습니다.
그 이유로는 첫번째는, 과제를 수행하기 위해 필요한 조건들을 완벽하게 숙지하지 않고, 만들었기 때문에 중간중간 수정하면서
코드도 수정하게 되었습니다. <br>
두번째로는 spring의  db설계 및 api 설계 하는것이 조금 낯설었고, db 관계를 정의하는데 Spring JPA Data 문법에 익숙하지 않아서
수정이 있었습니다. <br>

충분히 spring에 익숙해지고 내가 만들어야할 서비스에 대해서 고민하면서 설계를 한다면, 개발을 적은시간에 효율적으로 진행 할 수 있을거라 생각합니다.

 

Q3. 
<br>Refresh Token을 사용하는 이유가 뭘까요?

Q4.
<br>3번의 이유로 사용한다면, 왜 매번 Access Token과 Refresh Token을 모두 재발급 할까요? 만료 시간과 관련하여 고민/검색 해보세요!

A3,4. 
우선 보안을 위해 Refresh Token을 사용합니다. Reresh token의 사용목적은 만료된 Access Token 재발급에 있습니다. 
 Access token의 유효시간을 짧게하고, refresh token을 통해 access token을 재발급 받아 사용하기 위함입니다. <br>
여기서 든 의문은 <b>"만약 Access Token의 탈취문제처럼 Refresh Token도 탈취당하면 어떻게 처리하지?"</b> 였습니다
<br>
이에 대한 해결방안으로는 데이터베이스에 refreshToken과 AccessToken을 1대1 맵핑하고 access Token, refreshToken을 두개 다 제대로 보내지 않으면
탈취당한 것으로 생각하고 token들을 만료시키는 방법을 사용하라는 것입니다.
만약 refreshToken Aceess Token 모두 탈취당한다면, 백엔드 로직을 강화해서 막는 방법을 사용해야 한다고 합니다.

<br><b>"but then again there is nothing like 100% security"</b> 

<br>100%로의 보안은 없다고 합니다. 최대한 안뚫리게 architecture & 로직을 설계하는 것이 중요하다고
생각합니다.

