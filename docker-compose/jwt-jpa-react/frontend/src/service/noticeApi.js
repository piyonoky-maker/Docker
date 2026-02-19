import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_SPRING_IP, // http://localhost:8000/
  headers: { "Content-Type": "application/json" }
})

// 요청 시 localStorage의 accessToken을 Authorization 헤더에 붙임
// 클라이언트에서 서버로 요청이 있을 때 마다 자동으로 헤더를 붙여줌.
// 헤더에는 Bearer accessToken값이 담김.
api.interceptors.request.use((config) => {
  const accessToken = window.localStorage.getItem("accessToken")
  if(accessToken){
    config.headers.Authorization = `Bearer ${accessToken}`
  }
  return config
})

// 공지사항 등록하기
export const noticeInsertDB = async (notice) => {
  //axios가 아닌 api변수로 post함수 호출한 것
  const res = await api.post('notice', notice) //@RequestBody - json 포맷
  console.log(res.data)
  return res 
}

//목록 조회
export const noticeListDB = async(params) => {
  const query = {}
  const res = await api.get('notice/list', {params: query})
  console.log(res.data)
  return res 
}

/*
위 코드는 Axios인스턴스를 하나 만들어서 모든 요청에 공통 설정을 적용하고,
요청을 보내기 직전에 인터셉터로 Authorization 헤더에 토큰을 자동으로 
붙이는 구조임. 

변수 api는 axios의 커스텀 버전으로 선언함.
매번 axios.get(url, headers, ...)이렇게 안 쓰고 api.get("notice/list")처럼 
공통 설정이 자동 적용됨.

baseURL
- import.meta.env.VITE_SPRING_IP는 VITE환경변수임. 접두어 고정임.
- 예) http://localhost:8000/ 이면
  - api.get("notice/list") -> http://localhost:8000/notice/list

Content-Type
- 기본 헤더로 JSON 지정
- POST/PUT 에서는 body를 JSON으로 보낸다는 의미임. 

인터셉터가 하는 일
- api.get(...), api.post(...)호출 -> 실제 네트워크 요청이 나가기 전에 여기로 먼저 옴.
- 여기서 config는 이번 요청의 설정 객체임
- Authorization 헤더 자동 추가
  - Spring Securit JWT필터에서 일반적으로 이 포맷을 기대함.

즉 
api.get("notice/list") 와 같이 호출해도 실제 요청은 내부적으로

GET /notice/list
Authorization: Bearer eyDhbGrid .....
Content-Type:  application/json

위와 같이 나감. 

결론
중복제거
  - 매  API마다 토큰 붙이는 코드 반복 안함.
누락방지
  - 어떤 API를 호출해도 자동으로 붙어서 401번 403번 에러 피함
유지보수 쉬움
  - 토큰 정책 변경시 한 군데만 수정함. 
*/