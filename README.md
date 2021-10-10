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

![keycloak 사용자관리(admin)](https://user-images.githubusercontent.com/65889807/136679810-8611b0ec-a223-4e93-81bd-4cc68712274c.png)

----------- 
**4. 자유 게시판**
  * 로그인 성공 시 Session 저장
  * profile -> logout시 Session 삭제
  * 게시판 리스트 출력
  
![Main_freeboard](https://user-images.githubusercontent.com/65889807/132849714-5bb2a688-5a2a-41ef-84fd-52461a8c8291.png)

----------- 
**5. 게시글 작성**
   * 제목, 내용(3000Bytes 제한), 파일업로드(100MB이하) 구현
   
![Board_Insert](https://user-images.githubusercontent.com/65889807/132849034-65d25cd6-f177-49f8-b761-4577c493c5af.png)

----------- 
**6. 게시글 상세**
   * 게시글 수정 기능 구현(자기가 쓴 게시글만 수정버튼 보임)
   * 게시글 삭제 기능 구현(자기가 쓴 게시글만 삭제버튼 보임)
   * 댓글 기능 구현(자신이 쓴 댓글만 삭제 가능)
   * 추천기능(1번만 가능, 한번 더 누를 시 추천 취소) 구현
   * 조회수, 추천 수, 댓글 수 출력
  
 ![Board_detail](https://user-images.githubusercontent.com/65889807/132850962-e8704895-9ce1-4609-b6a8-811dd665746e.png)

----------- 
**7. 영화 검색**
   * 네이버 영화 검섹 API 기능 사용
   * 영화 제목, 개봉(년), 감독, 주요 배우, 관객 수 제공
   
![Movie_research](https://user-images.githubusercontent.com/65889807/132849866-928a7ff1-7c87-44f5-a927-5a415afaeefb.png)

 ----------- 
**8. 지역 검색**
  * 네이버 지역 검색 API 기능 사용
  * Category, 이름, 링크, 도로명 주소 제공(최대 5개)
  * Excel 버튼을 이용해 다운로드 가능 
  * 링크 클릭시 홈페이지로 이동

![Location_research](https://user-images.githubusercontent.com/65889807/132850327-d46efa4c-6e99-427e-bf50-c7287d2c4a9e.png)

----------- 
**9. 기록**
  * 자유 게시판에서 추천 누른 게시글 리스트 출력
  * 추천누른 시간 제공
  
![Recommend](https://user-images.githubusercontent.com/65889807/132850435-84a78bd0-1306-431b-ab82-6437993fdbd4.png)

----------- 
**10. 회원 정보(권한: User)**
   * 사용자 정보(아이디, 이름, 나이, 권한, 등록일, 비밀번호)
   * 비밀번호 변경 기능(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)
   * 회원 탈퇴 기능
   * 댓글 작성했던 게시글 리스트 출력(댓글 등록 날짜 제공)

![User_info](https://user-images.githubusercontent.com/65889807/132850630-68bdc55e-fc5a-48e3-83b0-17e264f68329.png)

-----------
**11. 회원 정보(권한: Admin)**
   * 사용자 정보(아이디, 이름, 나이, 권한, 등록일, 비밀번호)
   * 비밀번호 변경 기능(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)
   * 회원 탈퇴 기능
   * 사용자 리스트 출력(사용자 등록일 제공) 

![User_Info_Admin](https://user-images.githubusercontent.com/65889807/132851443-d4ba0849-afa9-48ec-88b5-1e11adf52cc2.png)
  

-----------
**기술 스택**
* Spring Boot
* Spring Security
* thymleaf
* AJax
* Bootstrap
* Maven
* KeyCloak Admin API

-----------
**프로젝트 정보**
* 신승용 - (ssyong917@naver.com)

----------- 
