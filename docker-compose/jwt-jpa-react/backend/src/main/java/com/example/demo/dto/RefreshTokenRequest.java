package com.example.demo.dto;

import lombok.Data;
//사용자로 부터 입력 받거나 혹은 화면으로 부터 읽어들인 값을
//서버에 전달할 때
//view계층을 리액트로 결정하였다.
//json포맷이나 폼 전송을 통해서 사용자가 입력한 값을 청취한다.
@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
