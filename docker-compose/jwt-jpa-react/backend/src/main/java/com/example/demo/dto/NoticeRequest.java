package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
//서버 입장에서 리액트에서 사용자가 입력한 값을 담는 클래스
//string title = request.getParameter("title")
//String content = request.getParameter("content")
//String writer = request.getParameter("writer")
@Data
public class NoticeRequest {
    @NotBlank(message = "제목은 필수입니다")
    @Size(max = 100)
    private String title;
    @NotBlank(message = "작성자는 필수입니다")
    @Size(max = 50)
    private String writer;
    @NotBlank(message = "내용은 필수입니다")
    @Size(max = 200)
    private String content;
}
