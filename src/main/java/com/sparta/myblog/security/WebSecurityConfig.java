package com.sparta.myblog.security;


import com.sparta.myblog.security.filter.FormLoginFilter;
import com.sparta.myblog.security.filter.JwtAuthFilter;
import com.sparta.myblog.security.jwt.HeaderTokenExtractor;
import com.sparta.myblog.security.provider.FormLoginAuthProvider;
import com.sparta.myblog.security.provider.JWTAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    private final JWTAuthProvider jwtAuthProvider;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final HeaderTokenExtractor headerTokenExtractor;


    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManagerBuilder auth) throws Exception {
        //인증 (Authentication)**: 사용자 신원을 확인하는 행위
        //인가 (Authorization)**: 사용자 권한을 확인하는 행위
        auth
                .authenticationProvider(jwtAuthProvider)
                .authenticationProvider(formLoginAuthProvider());

        http.csrf().disable();

        http
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
//                //로그인 기능
//                .formLogin()
//                //로그인 view 제공 (GET /api/login)
//                .loginPage("/api/login")
//                //로그인 처리 (POST /api/login)
//                .loginProcessingUrl("/api/login")
//                //로그인  처리후 성공시 URL
//                .defaultSuccessUrl("/")
//                .failureUrl("/api/login?error")
//                .permitAll()
//            .and()
                //로그아웃 기능 허용
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/")
                .permitAll();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //사용하는 필터 만들기
    @Bean
    public FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter(authenticationManager(authenticationConfiguration));
        formLoginFilter.setFilterProcessesUrl("/api/login");
        formLoginFilter.setAuthenticationSuccessHandler(formLoginSuccessHandler());
        formLoginFilter.afterPropertiesSet();
//        System.out.println(authenticationManager(authenticationConfiguration));
        return formLoginFilter;
    }

    //사용하는 필터 만들기
    private JwtAuthFilter jwtFilter() throws Exception {

        List<String> skipPathList = new ArrayList<>();

        // Static 정보 접근 허용
        skipPathList.add("GET,/CSS/**");
        skipPathList.add("GET,/JS/**");

        // 회원 관리 API 적용
        skipPathList.add("GET,/api/**");
        skipPathList.add("POST,/api/signup");
        skipPathList.add("POST,/api/userinfo");

        //기본 페이지 설정
        skipPathList.add("GET,/");
        skipPathList.add("GET,/favicon.ico");

        FilterSkipMatcher matcher = new FilterSkipMatcher(skipPathList, "/**");
        JwtAuthFilter filter = new JwtAuthFilter(headerTokenExtractor, matcher);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public FormLoginSuccessHandler formLoginSuccessHandler() {
        return new FormLoginSuccessHandler();
    }

    @Bean
    public FormLoginAuthProvider formLoginAuthProvider() {
        return new FormLoginAuthProvider(encodePassword());
    }


}
