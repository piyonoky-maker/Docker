package com.example.demo.dto;

import lombok.Data;

@Data
public class NoticeSearchCondition {
    private String gubun;//title, content, writer
    private String keyword;;//사용자가 입력한 문자열
}