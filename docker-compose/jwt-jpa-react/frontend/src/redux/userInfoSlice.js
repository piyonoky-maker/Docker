import { createSlice } from '@reduxjs/toolkit'

const userInfoSlice = createSlice({
  name: 'userInfoSlice',
  initialState: {},
  reducers: {
    setMemberInfo:(state, action) => {
      //action뒤에 payload를 붙이는 이유
      //Redux에서 action은 요청 객체임.
      //어떤 일을 할지 : type
      //그 일을 하는데 필요한 데이터 접근할 때 payload를 붙이도록 되어 있음.
      state.id = action.payload.id
      state.email = action.payload.email 
      state.username = action.payload.username 
      state.role = action.payload.role 
      state.provider = action.payload.provider
      state.providerId = action.payload.providerId
    }
  }
})
// 화면 컴포넌트에서 dispatch(setMemberInfo(member)) 형태로 사용 
// 8000번 포트로 요청한 응답을 받아서 dispatch(setMemberInfo(member))호출 하면
// 자동으로 payload에 담김.
export const { setMemberInfo } = userInfoSlice.actions
export default userInfoSlice