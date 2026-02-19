## Github Actions 실습
Github Actions
```text
코드를 Github에 push하는 순간 자동으로 실행되는 작업을 
정의하는 도구이다.

코드를 올리면
-> 테스트 돌리고
-> 빌드하고
-> 서버에 배포까지 자동으로 해줌

장점
->실수 제거
-> 배포 표준화
-> 팀 협업에 유리
-> push = 배포(DevOps)

개발자는 코드만 작성하면 되고
배포는 Actions가 한다. 

```
### 실습 순서
1. 새로운 프로젝트 폴더를 만들기
2. Github Actions를 실행시키기 위해서는 반드시 `.github/workflows`<br>
디렉토리에 *.yml 또는 *.yaml의 확장자로 파일을 작성한다.<br>
  - 프로젝트 폴더의 최상단에 만들어 준다.<br>
3.
```yml
on:
  push:
    branches:
    # main브랜치에 push될 때 아래 workflow를 실행해줘
      - main
# 하나의 workflow는 1개 이상의 job이 올 수 있다.
jobs:
  My-Job:
    #Github Actions를 실행할 서버 종류 선택
    runs-on: ubuntu-latest
    # step: 특정한 작업을 수행하는 단위
    # job은 여러개의 step으로 구성됨.
    steps:
      - name: Hello World
        run: echo "Hello World"
      - name: 여러 문장 작성하기
        run: |
          echo "Have"
          echo "a "
          echo "Good Time" 
      - name: 변수 사용이 가능하다.
        run: |
          echo $GITHUB_SHA
          echo $GITHUB_REPOSITORY
```

```bash
git clone https://github.com/slalom0914/security-server.git

chmod +x gradlew
chmod 744 gradlew

./gradlew clean build

build/libs/XXX.jar

chmod 744 XXX.jar

nohup java -jar 프로젝트명SNAPSHOT.jar &

sudo lsof -i:8080

curl http://localhost:8080 


```

```bash
cd /home/kiwi/security-server
git pull origin main
./gradlew clean build
sudo fuser -k -n tcp 8080 || true
nohup java -jar build/libs/*SNAPSHOT.jar > ./security.log 2>&1 &
```

```yml
# deploy-security.yml
on:
  push:
    branches:
      - main

jobs:
  deploy:
    runs-on: ubuntu-latest
  
  steps:
    - name: SSH로 EC2 ubuntu에 접속하기
      uses: appleboy/ssh-action@v1
      with:
        host: ${{ secrets.EC2_HOST }}
        username: ${{ secrets.EC2_USERNAME }}
        key: ${{ secrets.EC2_PRIVATE_KEY }}
        script_stop: true
        script: |
          cd /home/kiwi/security-server
          git pull origin main
          ./gradlew clean build
          sudo fuser -k -n tcp 8080 || true
          nohup java -jar build/libs/*SNAPSHOT.jar > ./security.log 2>&1 &
```