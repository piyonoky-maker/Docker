## 도커 CLI 실습

이미지 전체 삭제시

```bash
docker image rm $(docker images -q)
docker image rm -f $(docker images -q)
```

```powershell
docker image rm (docker images -q)
docker image rm -f (docker images -q)
```

이미지(Image)는 실행파일이고, 컨테이너(Container)는 실행 중인 프로세스이다.<br>
이미지는 설계도, 컨테이너는 실행 결과물이다.<br>
개발자는 이미지를 만들고, 서버는 컨테이너를 돌린다.<br>

### Docker Image란

```text
컨테이너를 만들기 위한 설계도(템플릿)
실행에 필요한 모든 것이 들어 있음
  - OS 레이어
  - 런타임(JDK, Node, Python 등)
  - 라이브러리
  - 어플리케이션 코드
```

이미지 특징<br>

- 읽기 전용<br>
- 여러 레이어(layer)로 구성
- 한 번 만들어지면 변경 불가함.<br>

비유<br>
이미지 = 붕어빵 틀<br>
컨테이너 - 붕어빵<br>

이미지 레이어 구조<br>

```text
[App Code Layer]
[Library Layer]
[Runtime Layer]
[OS Base Layer]
```

-> 변경된 레이어만 다시 빌드함<br>
-> 그래서 빠름<br>

### 실습 내용

1. nginx 이미지를 내려 받는다.
2. nginx이미지를 활용한 컨테이너를 생성하고 실행한다.
3. 생성과 실행일 때 차이는 컨테이너의 상태가 Create이고
   컨테이너가 상태가 Up으로 바뀌면 실행중임.
4. 브라우저에서 http://localhost 엔터했는데 nginx페이지가 보이지 않는다.
5. 이유가 뭘까요?

- 호스트 컴퓨터의 네트워크와 컨테이너 네트워크는 별개이다.
- 호스트 컴퓨터에서 http://localhost 했을 때 페이지를 볼 수 없다.
- 왜냐면 http://localhost 는 컨테이너 내부의 요청 URL임.
- 해결 방법은 호스트 컴퓨터의 포트를 별도로 선언해줌.
- 만일 호스트컴퓨터의 포트 번호를 5000번이라고 하면 http://localhost:5000 함.

이미지 삭제시 확인할 것

- 컨테이너가 실행 중 이거나 또는 컨테이너가 존재하면 이미지 삭제는 불가함.
- 컨테이너를 중단하고 컨테이너를 삭제까지 한 다음에 이미지 삭제가 가능함.

```bash
docker ps #ps는 process status의 약자

docker stop 컨테이너ID

docker image rm nginx # 컨테이너가 존재하므로 삭제 불가함.
#Error response from daemon: conflict: unable to delete nginx:latest
#(must be forced) - container 39cf09e7cca7 is using its referenced image c881927c4077
# 데몬(daemon): 백그라운드에서 계속 실행되면서 특정 서비스를 제공하는 프로세스를 말함.
# 사용자가 터미널에서 직접 실행 종료하지 않아도 시스템이 필요할 때 자동으로 동작함.
docker rm 컨테이너ID
# 바로 위에서 컨테이너를 삭제했으므로 아래서 삭제 가능함.
docker image rm nginx
```

### 컨테이너 생성 및 실행하기

```bash
docker run 이미지명[:태그명]

docker run nginx
# 이미지가 존재하는지 체크하고 없으면 다운로드 받음.
# 컨테이너 생성하고 컨테이너를 실행도 시킴
# 포그라운드로 실행되어서 로그를 실시간으로 볼 수 있음.
```
