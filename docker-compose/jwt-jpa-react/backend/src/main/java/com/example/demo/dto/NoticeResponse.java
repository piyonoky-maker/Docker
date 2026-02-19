package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
//조회 결과를 mysql서버로 부터 받아서 react에게 전달함.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponse {
    private Long no;
    private String title;
    private String writer;
    private String content;
    private Timestamp createDate;
}
