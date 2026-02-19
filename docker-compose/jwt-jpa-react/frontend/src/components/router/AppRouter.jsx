import React from 'react'
import { Route, Routes } from 'react-router-dom'
import HomePage from '../pages/HomePage'
import BoardPage from '../pages/BoardPage'
import PrivateRouter from './PrivateRouter'
import MyPage from '../pages/mypage/MyPage'
import ErrorPage from '../pages/error/ErrorPage'
import AdminPage from '../pages/admin/AdminPage'
import { useSelector } from 'react-redux'
import NoticePage from '../pages/NoticePage'
// 내부 페이지 라우팅 + Role기반 보호
const AppRouter = () => {
  //리덕스로 관리할 변수값
  //TODO 리덕스에서 로그인 사용자 정보(특히 role)를 꺼내는 코드 추가
  //ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
  const memberData = useSelector((state) => state.userInfoSlice)
  console.log(memberData.role)
  return (
    <Routes>
      {/* 누구나 접근 가능(로그인 이후 영역이므로 AuthProvider는 통과한 상태) */}
      <Route path='/home' element={<HomePage />} />
      {/* 공지사항 */}
      <Route
        path='/notice'
        element={
          <PrivateRouter role={memberData.role} allowedRoles={['ROLE_USER','ROLE_MANAGER','ROLE_ADMIN']}>
            <NoticePage />
          </PrivateRouter>
        }
      >
      </Route>      
      {/* 게시판 */}
      <Route
        path='/board'
        element={
          <PrivateRouter role={memberData.role} allowedRoles={['ROLE_USER','ROLE_MANAGER','ROLE_ADMIN']}>
            <BoardPage />
          </PrivateRouter>
        }
      >
      </Route>
      {/* 관리자페이지 - ROLE_ADMIN만 허용 */}
      <Route
        path='/admin'
        element={
          <PrivateRouter
            role={memberData.role}
            allowedRoles={['ROLE_ADMIN']}
          >
            <AdminPage />
          </PrivateRouter>
        }
      > 
      </Route>      
      {/* 마이페이지(대시보드) */}
      <Route
        path='/mypage'
        element={
          <PrivateRouter
            role={memberData.role}
            allowedRoles={['ROLE_USER']}
          >
            <MyPage />
          </PrivateRouter>
        }
      > 
      </Route>
      {/* 권한이 없을 때 보내는 페이지 */}
      <Route path='/error' element={<ErrorPage />} />
    </Routes>
  )
}

export default AppRouter
