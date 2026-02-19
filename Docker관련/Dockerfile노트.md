## Dockerfile 노트

```bash
docker pull nginx
```
- Docker이미지는 Dockerhub를 통해 다운받아 사용이 가능함.
- 도커이미지는 어떻게 만드는 걸까?

### Dockerfile이란?
- Docker이미지를 만들게 해주는 파일이다.
Dockerfile을 통해서 Docker이미지를 만들 수 있다.

### FROM: 베이스 이미지 생성
- FROM은 베이스 이미지를 생성하는 역할을 한다.
- 만일 Spring Boot프로젝트나 NodeJS프로젝트를 이미지로 만들고 싶다면.
- 누군가는 JDK가 깔려 있었으면... 또 다른 이는 NodeJS가 설치되어 있으면...
- 필요에 따라서 베이스 이미지를 고를 수 있다.

```dockerfile
FROM [이미지명]
FROM [이미지명]:[태그명]
# 만일 태그명을 적지 않으면 latest버전을 사용한다.
```

```bash
# 이미지 생성하기
docker build -t ubuntu-server .
# 이미지 목록 보기
docker image ls
# 이미지로 컨테이너 실행하기
docker run -d ubuntu-server
# 컨테이너 목록 보기
docker ps
# 컨테이너가 실행되고 나면 자동 종료됨
# 컨테이너 로그 보기
docker logs 컨테이너ID
# 컨테이너 내부 들어가기
docker exec -it 컨테이너ID bash
```

### COPY: 파일 복사(이동)
- COPY는 호스트 컴퓨터에 있는 파일을 복사해서 컨테이너로 전달함

사용법<br>
```dockerfile
COPY [호스트 컴퓨터에 있는 복사할 파일의 경로][컨테이너 파일이 위치할 경로]
COPY a.txt /a.txt
```

### RUN 과 ENTRYPOINT
`RUN`은 **이미지 생성 과정** 에서 필요한 명령어를 실행할 때 사용<br>
`ENTRYPOINT`는 생성된 이미지를 기반으로 **컨테이너를 생성한 직후**에 명령어를<br>
실행시킬 때 사용함. 


### [실습] 백엔드 프로젝트를 도커로 실행하기
1. Spring Boot project 생성하기 - docker-spring

2. HomeController.java(@RestController)

3. CLI로 프로젝트 빌드 및 실행
```bash
./gradlew clean build -> build/libs/*.jar

java -jar app.jar -> 내장되어 있는 톰캣서버 기동(8080)

```

4. Dockerfile작성하기
```dockerfile
#1. 베이스 이미지 선택하기(jdk21-Dockerhub)
FROM eclipse-temurin:21-jdk
#2. 호스트에서 컨테이너로 복사(이동)하기
COPY build/libs/*SNAPSHOT.jar app.jar
#3. 컨테이너 실행 명령어 - JSON배열 쓰기한 것임
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

5. 이미지 생성하기
```bash
docker build -t docker-spring .
```

6. 이미지 생성 확인하기
```bash
docker image ls
```

7. 생성한 이미지를 컨테이너로 실행하기
```bash
docker run -d -p 8080:8080 docker-spring:latest
docker run -d -p 8081:8080 docker-spring:latest
docker run -d -p 8082:8080 docker-spring:latest
```

8. 컨테이너를 실행되고 있는지 확인하기
```bash
docker ps
```

9. 브라우저를 통해서 확인하기(또는 curl http://localhost:8080)

10. 컨테이너 로그 확인하기
```bash
docker logs [컨테이너ID]
```

11. 컨테이너 내부 들여다 보기
```bash
docker exec -it [컨테이너ID] bash
```

12. 실행시킨 컨테이너 중지 및 삭제하기, 이미지 삭제하기
```bash
docker stop [컨테이너ID]
docker rm (-f) [컨테이너ID]
docker image rm {이미지 ID값}
```

그림으로 이해하기