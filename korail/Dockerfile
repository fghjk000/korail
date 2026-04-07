# 1. OpenJDK 21 (Corretto 21) 사용
FROM amazoncorretto:21

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 정확한 JAR 파일을 복사
COPY build/libs/korail-0.0.1-SNAPSHOT.jar app.jar

# 4. 실행할 명령어 지정
ENTRYPOINT ["java", "-jar", "app.jar"]