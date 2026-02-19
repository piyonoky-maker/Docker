package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//클래스 앞에 @Configuration있으면 스프링 컨테이너가 기동할 때 스캔을 미리 함
@Configuration
@EnableMethodSecurity //시큐어 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig {
    //개발자가 인증을 위해서 추가한 토큰 검증 필터를 추가하기
    private final JwtTokenFilter jwtTokenFilter;
    //메서드 앞에 @Bean있으면 스프링 컨테이너로 부터 객체에 대한 관리를 받음
    //메서드 인데 리턴타입이 객체이다.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("""
        ROLE_ADMIN > ROLE_MANAGER
        ROLE_MANAGER > ROLE_TEACHER
        ROLE_TEACHER > ROLE_USER
    """);
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(configurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                //Basic인증 비활성화
                //사용자이름과 비번을 Base64로 인코딩해서 인증값으로 활용하는 방식
                //토큰방식은 signature부분에 암호화가 들어가는 방식이므로 basic과는 다름
                .httpBasic(AbstractHttpConfigurer::disable)
                //세션 방식을 비활성화
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //특정 url패턴에 대해서는 인증처리 제외할 께(Authentication객체 생성)
                .authorizeHttpRequests((requests) -> requests
                        //공개되는 엔드포인트 작성
                        .requestMatchers(
                                "/auth/signin","/auth/refresh", "/auth/signup")
                        .permitAll()
                        // React SPA 정적 리소스 및 클라이언트 라우팅 경로 (동일 오리진 배포 시)
                        .requestMatchers("/", "/index.html", "/assets/**", "/vite.svg", "/favicon.ico")
                        .permitAll()
                        // SPA 클라이언트 라우팅: GET으로 오는 페이지 요청은 index.html 서빙을 위해 허용
                        .requestMatchers(HttpMethod.GET, "/**")
                        .permitAll()
                        // 그 외(POST 등)는 인증 필요. (API는 @RolesAllowed 등으로 보호)
                        .anyRequest().authenticated())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }// end of defaultSecurityFilterChain
    //TODO - CORS이슈 5173 -> 8000, 8000-> 5173
    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("*"));//모든 HTTP메서드 허용
        configuration.setAllowedHeaders(Arrays.asList("*"));//모든 헤더값 허용
        configuration.setAllowCredentials(true);//자격증명 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //모든 url패턴에 대해서 cors 허용 설정
        //별 두개가 있으면 디렉토리까지 파고들어 간다.
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
/*
@Configuration - @Bean
@Bean -  클래스
@Beans - 클래스 여러 개
의사결정은 사람 - 생각사람 - 사고훈련
 */