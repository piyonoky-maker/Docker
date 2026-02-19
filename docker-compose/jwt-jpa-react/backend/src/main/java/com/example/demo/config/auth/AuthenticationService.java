package com.example.demo.config.auth;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.MemberLoginDto;
import com.example.demo.dto.SignupRequest;
import com.example.demo.model.JwtAuthenticationResponse;
import com.example.demo.model.Member;
import com.example.demo.model.RoleType;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public JwtAuthenticationResponse signin(MemberLoginDto memberLoginDto) {
        log.info("signin");
        Member member = memberRepository.findByEmail(memberLoginDto.getEmail());
        log.info(member);//DB에서 꺼내온 정보들. member.getpassword().equals(memberLoginDto.getPassword())
        if (member == null) {//당신이 입력한 이메일로 검색했는데 조회결과가 없다.
            throw new BadCredentialsException("이메일이 존재하지 않습니다.");
        }
        //여기까지 왔다면 이메일이 존재한다는 뜻 -> 비번을 비교하는 코드가 누락됨.
        //ok가 true이면 비번이 일치함. false이면 비번이 틀립니다.
        boolean ok = bCryptPasswordEncoder.matches(memberLoginDto.getPassword(), member.getPassword());
        if(!ok){
            throw new BadCredentialsException("비밀번호가 맞지 않습니다.");
        }

        Long id = member.getId();
        String username = member.getUsername();
        String role =  member.getRole().toString();
        String email = member.getEmail();
        //TODO - accessToken과 refreshToken 추가
        String accessToken = jwtTokenProvider.createToken(email, role);
        String refreshToken = jwtTokenProvider.createRefreshToken(email, role);
        JwtAuthenticationResponse jaResponse = new JwtAuthenticationResponse();
        jaResponse.setId(id);
        jaResponse.setAccessToken(accessToken);
        jaResponse.setRefreshToken(refreshToken);
        jaResponse.setUsername(username);
        jaResponse.setRole(role);
        jaResponse.setEmail(email);
        return jaResponse;
    }

    public JwtAuthenticationResponse refreshToken(String prefreshToken) {
        String email = jwtTokenProvider.extractEmail(prefreshToken);
        Member rmember = memberRepository.findByEmail(email);
        if(jwtTokenProvider.isTokenValid(prefreshToken, rmember)) {
            String accessToken = jwtTokenProvider.createToken(rmember.getEmail(), rmember.getRole().name());
            String refreshToken = jwtTokenProvider.createRefreshToken(rmember.getEmail(), rmember.getRole().name());
            JwtAuthenticationResponse jaResponse = new JwtAuthenticationResponse();
            jaResponse.setRefreshToken(refreshToken);
            jaResponse.setAccessToken(accessToken);
            jaResponse.setRole(rmember.getRole().name());
            jaResponse.setEmail(rmember.getEmail());
            jaResponse.setUsername(rmember.getUsername());
            jaResponse.setId(rmember.getId());
            return  jaResponse;
        }
        return null;
    }

    public Member signup(SignupRequest signupRequest) {
        Member member = new Member();
        member.setEmail(signupRequest.getEmail());
        member.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
        member.setUsername(signupRequest.getUsername());
        member.setRole(RoleType.valueOf(signupRequest.getRole()));
        return memberRepository.save(member);
    }
}
