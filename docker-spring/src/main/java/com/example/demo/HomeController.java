package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        System.out.println("home()호출 성공");
        return "EC2 자동배포 실습 테스트";
    }
}
