#### 능력단위명 - 2001020214_23v6 애플리케이션 배포

(총계 : /64 -> 38.4점 이상)

- 애플리케이션 배포 환경 구성하기(1) : /4
- 애플리케이션 배포 환경 구성하기(2) : /4
- 애플리케이션 배포 환경 구성하기(3) : /4
- 애플리케이션 배포 환경 구성하기(4) : /4
- 애플리케이션 소스 검증 하기(1) : /4
- 애플리케이션 소스 검증 하기(2) : /4
- 애플리케이션 소스 검증 하기(3) : /4
- 애플리케이션 빌드하기(1) : /4
- 애플리케이션 빌드하기(2) : /4
- 애플리케이션 빌드하기(3) : /4
- 애플리케이션 빌드하기(4) : /4
- 애플리케이션 빌드하기(5) : /4
- 애플리케이션 배포 하기(1) : /4
- 애플리케이션 배포 하기(2) : /4
- 애플리케이션 배포 하기(3) : /4
- 애플리케이션 배포 하기(4) : /4

#### 채점 기준표

문제 번호 배점 채점 기준(주요 관찰 항목)<br>
분류
문제1-10점 Initializr 설정(Java21, Gradle) (5점), application.yml 설정 (5점)  
문제2 30점 RoleHierarchy 계층 설계 및 권한별 접근 제어 구현 (20점), 로그인/로그아웃,  
예외 처리 및 정적 리소스 허용 설정 (10점)
문제3 10점 Spring Security 설정 코드 작성 정확성 (10점) — SecurityFilterChain,  
PasswordEncoder, RoleHierarchy 등  
문제4 20점 Docker 환경 설정 6단계 절차 설명 (15점: Dockerfile 작성, 이미지 빌드, 실행, 확인), 컨테이너 관리 명령어 설명 (5점)  
문제5 30점 AWS EC2 업로드 및 Docker 설치 과정 (10점), 컨테이너 실행 및 배포 성공 여부 (10점), 인바운드 규칙 설정 및 접속 테스트 캡처 (10점)

#### 주어진 문제를 보고 해당 내용에 대해 작성해 주세요.

시험 문제는 총 5개로, 각 문제는 다른 난이도와 점수를 가집니다.<br>
시험 문제는 교육생제출용 폴더 내 답안지에 markdown으로 작성되어 있으며, 요구사항에 따라 답안을 작성해야 합니다.<br>
시험 문제는 애플리케이션배포내 나와있는 문제와 점수로 채점됩니다.<br>
시험 문제는 Spring Boot Security 애플리케이션 준비하기, Role계층 설정, 스프링 시큐리티 설정, 도커환경 설정, AWS EC2 Docker배포하기 작성을 평가합니다.<br>
시험 문제는 모두 2시간 안에 풀어야 합니다.<br>
채점기준표 관찰항목에 부합되는 경우 부분 점수는 인정합니다.<br>
작성한 답안지 내용이 요구사항에 부합하지 않거나 일부 누락되었을 경우 해당 항목은 그대로 감점처리 됩니다.<br>

주의 - 테스트한 결과를 캡쳐하여 첨부한 경우 반드시 md파일을 pdf로 변환하여 제출하여야 캡쳐한 이미지를 볼 수 있습니다.  
답안지 제출시에 반드시 pdf로 변환하여 제출하시기 바랍니다.(이미지가 안보이면 채점할 수 없습니다.)

#### 평가문제

- Overview

1. Spring Boot Security를 활용하여 인증과 인가를 구현해 보았습니다.
2. 인가 규칙은 다음과 같습니다.

- /user/\*\*는 로그인만 되어 있으면 접근이 가능합니다.
- /teacher/**, /manager/**, /admin/\*\* 는 각각 역할(Role) 필요.
- 그 외 경로는 전부 허용(anyRequest().permitAll()) 합니다.
- 유저는 관리자 페이지 접근이 안되지만 관리자는 유저페이지도 접근하고  
  관리자페이지도 접근가능하게 합니다.

3. 폼 로그인: /loginForm을 로그인 화면으로 쓰고, /loginProcess로 폼을 제출하면  
   시큐리티가 가로채서 로그인 처리합니다.
4. 로그아웃: /logout 으로 세션 종료, 쿠키 삭제 후 /로 이동합니다.
5. 예외처리 : 권한 부족시 /access-denied 로 보냅니다.

#### 문제1. Docker를 활용해 AWS EC2에 Spring Boot Security배포하기 (배점:100점)

- Spring Boot(Security 포함) 앱을 Docker 컨테이너로 만들고 AWS EC2에 배포하여 외부에서 접속 가능하게 하세요.
- 이 과정을 비전공자들도 잘 따라해 볼 수 있도록 메뉴얼을 작성해 주세요.
- 아래 내용은 반드시 포함되어야 합니다.
  - SpringBoot Security 프로젝트를 어떻게 만드는지 설명하세요.
  - Docker Image로 만드는 과정을 설명하세요.
  - AWS에 EC2인스턴스에서 이미지를 받아서 컨테이너로 어떻게 실행하는지 설명하세요.
  - 권한별로 작성된 페이지에 접속해 보고 테스트한 결과 화면을 캡쳐하여 첨부하시오.

(1) Spring Boot Security 애플리케이션 준비하기

- 프로젝트 생성하기(10점)
1. https://start.spring.io/ 접속한다.
2. Project에서는 Gradle-Groovy선택, Language는 Java, 
Spring Boot버전은 SNAPSHOT이 아닌 버전 중 3.5.10을 선택
Packageing은 jar선택, Java버전은 21을 선택
3. Dependencies에서 ADD DEPENDENCIES버튼 클릭
4. Spring Boot DevTools, Lombok, Spring web, Spring Security선택함
5. Explore버튼을 클릭하여 의존성 주입이 모두 적용되었는지 확인
6. Generate버튼을 눌러서 생성된 프로젝트를 다운로드 받는다.

(2) Role계층(관리자 > 매니저 > 교사 > 사용자) - 30점
상위 Role이 하위 Role을 포함하도록 구성하세요.

/loginForm 접속 가능, 실패 시 ?error=true 붙어오는지 확인해 보기(5점)

/loginProcess 제출 시 컨트롤러 없이 로그인 처리되는지 지켜본다(5점)

/user/\*\* 미로그인 접근 시 로그인 페이지로 리다이렉트 되는지 확인한다(5점)

TEACHER 계정으로 /teacher/** OK, /manager/** ,/admin/\*\* 차단되는지 확인한다.(5점)

로그아웃은 POST /logout로 동작하고 세션이 정리되는지 알아본다.(5점)

정적 리소스(css/js/images) 정상 로딩되는지 브라우저에서 요청해 본다.(5점)

```java

```

(3) 위 자가 테스트 체크리스트를 확인할 수 있는 스프링 시큐리티 설정 코드를 작성하시오. - 10점

```java

@EnableWebSecurity//스프링 시큐리티 필터가 스프링 필터 체인에 등록됨
@EnableMethodSecurity(prePostEnabled = true)//secured어노테이션 활성화
@Configuration
public class SecurityConfig {

  }

  @Bean
  GrantedAuthoritiesMapper authoritiesMapper(RoleHierarchy roleHierarchy) {
      // 로그인 시 ADMIN이면 MANAGER/TEACHER/USER 권한을 자동으로 '추가' 부여
      return new RoleHierarchyAuthoritiesMapper(roleHierarchy);
  }

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

  }
}
```

(4) Docker환경 설정하기를 6단계로 나누어 설명하시오.(각 5점 - 30점)

- Dockerfile 작성
- Docker 이미지 빌드
- Docker 컨테이너 실행

1. build하기 -> 이미지 생성하기

```bash
docker build -t spring-server .
```

2. 프로젝트 폴더 아래 Dockerfile 추가한다.

```dockerfile
#1. 베이스 이미지 선택
FROM eclipse-temurin:21-jdk
#2. 호스트에서 컨테이너로 복사하기
COPY build/libs/*SNAPSHOT.jar app.jar
#3. 컨테이너 실행 명령
# java -jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

3. 이미지 확인하기

```bash
docker image ls
```

4. 컨테이너 실행하기

```bash
docker run -d -p 8080:8080 spring-server
```

5. 현재 실행중인 컨테이너 확인

```sh
docker ps
```

6. 현재 실행중인 컨테이너 중지

```sh
docker stop [도커컨테이너아이디]
```

7. 도커에 등록된 컨테이너 삭제하기

```sh
# 아래 이미지 ID는 일부만 붙여도 삭제된다.
docker rm (-f) [컨테이너아이디]
```

(5) AWS EC2 Docker배포하기 - 총점 : 30점

- 로컬에서 개발된 프로젝트 파일을 EC2에 업로드 하는 방법을 작성하시오.(5점)

```sh
# 리눅스 환경에서 패키지 목록을 최신 상태로 갱신하는 명령문
sudo apt update

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

sudo apt-get update && sudo apt-get install docker-ce docker-ce-cli containerd.io

# 도커 관련 설치 파일
sudo apt-get update && sudo apt-get isudo apt updatestall docker-ce docker-ce-cli containerd.io
# 도커 버전 정보
sudo docker -v

# sudo 문장을 붙이지 않게 설정
sudo usermod -aG docker $USER
# 재실행을 해야함
sudo systemctl restart docker

# 도커를 시스템 관리자 명령으로 재시작
sudo systemctl restart docker

sudo apt update 

sudo apt install -y openjdk-21-jdk

```

- AWS EC2에 도커 설치하는 과정을 설명하시오.(5점)

```sh

```

- AWS EC2서버 도커 컨테이너에 서비스 올리는 과정을 설명하시오.(10점)

```sh

```

- AWS EC2서버에 배포된 서비스를 외부에서 접근하기 위해 필요한 인바운드 설정하는 방법을 작성하시오.(5점)

- AWS EC2인스턴스에 할당된 Public IP주소로 Hello World가 출력된 페이지를 캡쳐하여 첨부하시오.(5점)
