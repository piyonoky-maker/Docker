## 윈도우 WSL이란?

WSL(Windows Subsystem for Linux) <br>
- 윈도우 운영 체제에서 Linux환경을 실행할 수 있도록 설계된 기능.
- 실제 리눅스 커널을 사용할 수 있음. 
- Linux 네이티브한  성능을 제공하며 서버 환경과 유사

```bash
sudo apt update

java -version

sudo apt install openjdk-21-jdk

javac -version

sudo apt-get install apt-transport-https ca-certificates curl

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

# docker-ce: 도커엔진
# docker-ce-cli: docker 클라이언트 명령어
# containerd.io : 컨테이너 런타임(도커 내부에서 사용됨)
sudo apt-get update && sudo apt-get install docker-ce docker-ce-cli containerd.io

# docker 그룹 권한 추가
# $USER : 현재 로그인한 사용자 이름
sudo usermod -aG docker $USER

# docker 데몬 재시작
# systemctl : systemd 서비스 관리 명령
# restart docker : 도커 데몬을 재시작
sudo systemctl restart docker

# sudo로 docker ps 테스트
# 현재 실행 중인 컨테이너 목록 확인
# sudo를 붙인 이유:
# 아직 docker그룹 권한이 적용되지 않았을 때 사용
# 일단 root권한으로 되는지 확인하는 테스트 
sudo docker ps

# 그룹 권한 적용
# 현재 쉘 세션의 그룹을 docker 새로 시작하기
newgrp docker

docker ps
```