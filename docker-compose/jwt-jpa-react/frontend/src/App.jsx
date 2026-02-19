import "bootstrap/dist/css/bootstrap.min.css"
import { Route, Routes, useNavigate } from "react-router-dom"
import HomePage from "./components/pages/HomePage"
import BoardPage from "./components/pages/BoardPage"
import IsTokenExpiration from "./components/auth/IsTokenExpiration"
import { useEffect, useState } from "react"
import AppRouter from "./components/router/AppRouter"

function App() {
  const navigate = useNavigate()
  //token상태를 관리하기 위해서 useState로 변경하기
  const [token, setToken] = useState(()=>{
    const token = window.localStorage.getItem('accessToken')
    return token
  })
  //TODO - 토큰 유효시간 체크 - 파기/유지
  const isTokenExpire = IsTokenExpiration(token, setToken)
  console.log(isTokenExpire)// true이면 만료, false이면 유효
  useEffect(() => {
    if(isTokenExpire){
      window.localStorage.clear()
      //로그인 페이지로 이동
      //replace:true -> 뒤로 가기 눌러도 만료된 페이지로 돌아가지 않도록 함.
      navigate("/", {replace: true})
    }
  },[setToken, isTokenExpire, navigate])
  return (
    <>
      {/* 내부 페이지 라우팅 처리 */}
      <AppRouter />
    </>
  )
}

export default App
