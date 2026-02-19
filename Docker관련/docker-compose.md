docker-compose.yml은 컨테이너를 실행하고 관리하기 위한 설정 파일입니다. <br>
```yml
services:
  # =========================================
  # 1) DB 컨테이너: MySQL
  # =========================================
  my-db:
    image: mysql
    # ✅ 컨테이너 이름 고정 (docker ps에서 보기 쉬움)
    container_name: my-mysql

    environment:
      # ✅ 루트(최고관리자) 비밀번호
      MYSQL_ROOT_PASSWORD: 12345678

      # ✅ 최초 실행 시 kiwidb 데이터베이스 자동 생성
      MYSQL_DATABASE: kiwidb

      # ✅ 일반 계정 생성(실무에선 root 대신 일반 계정 권장)
      MYSQL_USER: kiwi
      MYSQL_PASSWORD: kiwi

    ports:
      # ✅ (호스트:컨테이너)
      # - 내 PC에서 3307로 접속하면, 컨테이너 내부 3306(MySQL)로 연결됨
      # - 즉, PC에서: localhost:3307 → 컨테이너 MySQL:3306
      - "3307:3306"

    volumes:
      # ✅ DB 데이터를 컨테이너 밖에 보관(컨테이너 삭제해도 데이터 유지)
      # - mysql_data라는 named volume을 /var/lib/mysql(MySQL 데이터 폴더)에 연결
      - mysql_data:/var/lib/mysql

    healthcheck:
      # ✅ DB가 "정상 기동"했는지 검사
      # - mysqladmin ping이 성공하면 healthy로 판단
      # - "-h localhost"는 컨테이너 내부에서 자기 자신 MySQL에 ping
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]

      # ✅ 5초마다 체크
      interval: 5s

      # ✅ 10번까지 재시도 (최대 약 50초 기다림)
      retries: 10


  # =========================================
  # 2) 앱 컨테이너: Spring Boot + (React 정적 포함)
  # =========================================
  app:
    build:
      # ✅ 빌드 컨텍스트(도커가 파일을 읽어오는 기준 폴더)
      # - "." 이면 docker-compose.yml 있는 폴더가 기준
      context: .

      # ✅ 사용할 Dockerfile 지정
      dockerfile: Dockerfile

    # ✅ 컨테이너 이름 고정
    container_name: my-app

    depends_on:
      my-db:
        # ✅ my-db가 "healthy" 상태가 된 이후에 app을 띄움
        # - 단순히 컨테이너 생성 순서가 아니라
        #   healthcheck 통과 후에 app 시작하게 만드는 게 핵심
        condition: service_healthy

    environment:
      # ✅ 스프링이 접속할 DB 주소
      # ⚠️ 여기서 localhost 쓰면 안 됨!
      # - app 컨테이너 안에서 localhost는 "app 자기 자신"을 의미
      # - DB 컨테이너로 가려면 서비스명(my-db)로 접근해야 함 (Docker DNS)
      # - 즉, jdbc:mysql://my-db:3306/...
      SPRING_DATASOURCE_URL: jdbc:mysql://my-db:3306/kiwidb?serverTimezone=Asia/Seoul

      # ✅ DB 계정/비밀번호
      SPRING_DATASOURCE_USERNAME: kiwi
      SPRING_DATASOURCE_PASSWORD: kiwi

    ports:
      # ✅ 내 PC에서 8000으로 접속하면 app 컨테이너의 8000으로 연결
      # - 브라우저: http://localhost:8000
      - "8000:8000"


# =========================================
# 3) 볼륨 선언: mysql_data (DB 영속 저장소)
# =========================================
volumes:
  mysql_data:

```