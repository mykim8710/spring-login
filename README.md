# Spring - Login

## Project Spec
- 프로젝트 선택
    - Project: Gradle Project
    - Spring Boot: 2.7.3
    - Language: Java
    - Packaging: Jar
    - Java: 11
- Project Metadata
    - Group: com.example
    - Artifact: login
    - Name: spring-login
    - Package name: com.example.login
- Dependencies: **Spring Web**, **Thymeleaf**, **Lombok**, **Validation**


## Spring - Login
- 간단한 회원가입 기능 추가
- 로그인 처리기능 구현
  - 쿠키를 사용한 로그인 처리
  - 세션을 사용한 로그인 처리
    - 직접 SessionManager정의 후 적용
    - 서블릿 HTTP 세션 : HttpSession적용
- 세션 타임아웃 설정
