리액트 프로젝트 자동배포하기(vite버전)

npm create vite@latest

framework설정 - react

variant설정 - javascript

```dockerfile
FROM node: 22-alpine

WORKDIR /app
# 의존성 설치 
COPY package*.json ./
RUN npm ci
# 소스 복사
COPY . .

# vite 기본 포트
EXPOSE 5173

# 컨테이너 밖에서 접속 가능하게 
CMD ["npm", "run", "dev","--","--host","0.0.0.0", "--port", "5173"]