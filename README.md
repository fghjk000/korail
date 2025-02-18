# 우리만의 코레일 제작

AWS 서버 EC2(t3.small)
Docker 컨테이너(Ubuntu 기반으로 Spring Boot, Nginx, MySQL[JPA 기반, RDB면 뭐든 상관없음], Redis)
+ AOP 적용
+ TDD(레이어별 단위테스트)
+ Spring Security[JWT-RefreshToken을 Redis 고려] + OAuth2.0 적용
+ GitHub Action으로 자동 배포(CICD)를 기본 시나리오 설정
