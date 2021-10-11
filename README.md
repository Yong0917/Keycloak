# [KeyCloak] Keycloak SSO Test
-----------

**개요**
* KeyCloak으로 Springboot을 연동해 간단하게 SSO 구축
----------- 

**주요 기능**
* 사용자 관리 (Admin)
* 사용자 생성 (Admin)
* 사용자 삭제 (Admin)
* 사용자 수정 (Admin)
* 아이디 중복체크 (Admin)
* 내정보 수정
* 내정보 변경
----------- 
**주요 기술**
* KeyCloak admin API 기능 사용(CRUD)
* 권한 별 기능 구현
* 로그인, 로그아웃 세션 처리 기능 구현
* SpringSecurity(Keycloak연동)


-----------
**키클락 설정**
* KeyCloak 설치
https://www.keycloak.org/downloads.html
* Realm 생성
![realm 생성](https://user-images.githubusercontent.com/65889807/136679211-5f70d7ff-1ce2-4aaf-b203-373f8431ab61.png)
-----------
* Client 생성 
- localhost:7443으로 들어오는 모든 요청은 keycloak에서 제공해주는 로그인 화면으로 redirect
![client 생성](https://user-images.githubusercontent.com/65889807/136679213-85968e0a-121d-4058-8402-16fe4ce7d1b3.png)
-----------
* Roles 생성
* ADMIN, USER 2개의 ROLE 생성
![Roles 설정](https://user-images.githubusercontent.com/65889807/136679297-d2cfc9d3-d9de-416a-aca3-b7d9e15fd903.png)
-----------
* User 생성
* 최초 비밀번호 변경 필수
![User 생성](https://user-images.githubusercontent.com/65889807/136679300-84b8fe27-1a94-4e36-8bf0-ec02cb47ac10.png)

-----------
**Sppring boot App 로그인 화면**
  * localhost 7443으로 들어오는 모든 요청을 자동으로 keycloak 로그인화면으로 redirect
  ![keycloak 로그인](https://user-images.githubusercontent.com/65889807/136679553-9ce81c85-5751-4f43-b561-693c9c01169e.png)

----------- 
**2. 메인 화면**
 * 최초 로그인시 Main 화면으로 redirect
 * 로그아웃 클릭시 keycloak login 화면으로 이동
 * ROLE - USER 로그인시 내정보 수정, 내 비밀번호 변경 메뉴만 보이게 설정
 ![keycloak main](https://user-images.githubusercontent.com/65889807/136679636-ef5ed3cc-b70c-4ecd-a9cd-200e26817842.png)
 * ROLE - ADMIN 로그인시 사용자 관리, 내정보 수정, 내 비밀번호 변경 메뉴 설정
 ![keycloak main(admin)](https://user-images.githubusercontent.com/65889807/136679778-17a1b073-a9ba-495e-b9fc-6fd266ce1788.png)

 ----------- 
**3. 사용자 관리 메뉴(ADMIN)** 
  * 유저 정보 가져오는 API 호출

![keycloak 사용자관리(admin)](https://user-images.githubusercontent.com/65889807/136679810-8611b0ec-a223-4e93-81bd-4cc68712274c.png)

----------- 
**4. 사용자 생성**
  * 아이디 중복확인 (아이디가 존재할 시 중복체크)
  * 아이디, 이름, 성, 비밀번호 ,이메일 빈 값으로 저장할 시 빈 값 체크 Alert
  * 비밀번호는 8글자 이상 16글자 이하, 특수문자 한개 이상포함. 안될 시 체크 Alert
  * 이메일형식 맞게 입력안하면 체크 Alert
  * 이메일은 중복가능 (Keycloak에서 duplicate email 체크해야 가능)
  * 등록 버튼 클릭시 Keycloak API 호출
 
  
![사용자 생성](https://user-images.githubusercontent.com/65889807/136688201-d7424ca5-6b36-4b3d-9f0b-66c13e1998ab.png)

----------- 
**5. 사용자 삭제**
   * 체크 박스 선택후 삭제가능.
   * 여러 개의 체크 박스 선택가능.
   * 삭제 버튼 클릭시 Keycloak API 호출
   
   ![사용자 삭제](https://user-images.githubusercontent.com/65889807/136688216-bbbb31fd-152d-4640-83a5-404290a634da.png)

----------- 
**6. 사용자 수정**
   * 아이디값 수정 못하게 readable 처리
   * 수정 버튼 클릭시 Keycloak API 호출
  
![사용자 수정](https://user-images.githubusercontent.com/65889807/136688226-13b1d9f5-15c5-4351-99dd-4cfa0f669822.png)


----------- 
**7. 내 정보 수정**
   * 내정보 가져오는 API 호출
   * 아이디값 수정 못하게 readable 처리
   * 이름, 성, 이메일 빈 값으로 저장할 시 빈 값 체크 Alert
   * 이메일형식 맞게 입력안하면 체크 Alert
   * 초기화 버튼 클릭 시 이름.성.이메일 초기화.
   * 수정 버튼 클릭시 Keycloak API 호출
   
![내 정보 수정](https://user-images.githubusercontent.com/65889807/136688234-ec1f2b9d-45cb-4b81-9c50-49248bdc31e9.png)

 ----------- 
**8. 내 비밀번호 변경**
  * 내정보 가져오는 API 호출
  * 비밀번호, 비밀번호 확인 값 다를시 text표출
  * 비밀번호는 8글자 이상 16글자 이하, 특수문자 한개 이상포함. 안될 시 체크 Alert
  * 초기화 버튼 클릭 시 비밀번호 초기화.
  * 수정 버튼 클릭시 Keycloak API 호출

![내 비밀번호 변경](https://user-images.githubusercontent.com/65889807/136688240-ff4ff36a-3423-418c-ba12-1a8eca26497f.png)

-----------
**기술 스택**
* Spring Boot
* Spring Security
* thymleaf
* AJax
* Bootstrap
* Maven
* KeyCloak
* SSO
* KeyCloak Admin API

-----------
**배운점 및 목표**
  * 기본적인 SSO 구현 구조와 오픈소스인 KeyCloak을 이용해 구현 및 테스트
  * KeyCloak Api를 이용해 기능 구현을 통해 기능 테스트
  * 1개의 Client로 진행하였지만 여러개의 Client을 등록해서 연계할 수 있게 구현 할 예정
  * Goggle, Naver, kakao 등 통합게시판에 로그인기능을 구현 할 예정
  
-----------
**프로젝트 정보**
* 신승용 - (ssyong917@naver.com)

----------- 
