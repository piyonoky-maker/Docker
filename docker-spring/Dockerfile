#1. 베이스 이미지 선택하기(jdk21)
FROM eclipse-temurin:21-jdk
#2. 호스트에서 컨테이너로 복사(이동)하기
COPY build/libs/*SNAPSHOT.jar app.jar
#3. 컨테이너 실행 명령어 - JSON배열 쓰기한 것임
ENTRYPOINT ["java", "-jar", "/app.jar"]
