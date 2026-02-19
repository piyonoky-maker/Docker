package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@Table(name="member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 50)
    private String email;
 /*
 mysql에는 RoleType이라는 것이 없음.
 Enum타입(열거형)-
 문제제기: role타입을 String으로 가져가면 ROLE_USER를 넣어야 되는데 그냥 user도 되고
 다른 문자열도 가능하므로 이것을 리액트에서 비교값으로 사용할 때 문제가 발생됨.
 유효한 값이 아닌 것도 들어갈 수 있다는 점이 문제다.
 그런데 Enum을 사용하면 정의된 타입만 사용하게 되므로 안전한 코드가 됨
 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private RoleType role;//ROLE_USER,ROLE_MANAGER ,ROLE_ADMIN
    @CreationTimestamp // 시간이 자동 입력됨
    private Timestamp createDate;//비워놔도 자동으로 들어간다.
    //소셜 로그인 - 카카오
    @Column(nullable = true, length = 50)
    private String provider;//google, kakao, naver
    @Column(nullable = true, length = 50)
    private String providerId; //uid, 카카오식별자, 네이버 식별자
}
