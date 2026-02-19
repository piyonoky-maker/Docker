package com.example.demo.repository;

import com.example.demo.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
// 같은 기능이더라도 장치에 따라 기능이 달라질 수 있다.
// 결정할 수 없다. 왜 결정할 수 없나? 장치마다 다르니까
// 메서드 뒤에 세미콜론으로 끝나면 메서드 호출
// 메서드에 대해서 선언만 해둔다. - 쿼리메서드 -> JPA
public interface MemberRepository extends JpaRepository<Member, Long> {
    //select * from member where email = ?
    //findBy는 규칙이고 email은 문법이다.
    public Member findByEmail(String email);
    //select * from member where providerId = ?
    public Member findByProviderId(String providerId);
}
