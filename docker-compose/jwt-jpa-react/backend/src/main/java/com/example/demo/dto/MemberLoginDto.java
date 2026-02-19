package com.example.demo.dto;

import lombok.Data;

@Data
public class MemberLoginDto {
    private String email;
    private String password;//해시값이 아닌 사용자가 입력한 비번 111, 123
}
