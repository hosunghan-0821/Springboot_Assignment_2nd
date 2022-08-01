package com.sparta.myblog.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder encodePassword(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{


        //인증 (Authentication)**: 사용자 신원을 확인하는 행위
        //인가 (Authorization)**: 사용자 권한을 확인하는 행위

        http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/CSS/**").permitAll()
                .antMatchers("/JS/**").permitAll()
                .anyRequest().authenticated()
            .and()
                //로그인 기능
                .formLogin()
                //로그인 view 제공 (GET /api/login)
                .loginPage("/api/login")
                //로그인 처리 (POST /api/login)
                .loginProcessingUrl("/api/login")
                //로그인  처리후 성공시 URL
                .defaultSuccessUrl("/")
                .failureUrl("/api/login?error")
                .permitAll()
            .and()
                //로그아웃 기능 허용
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/")
                .permitAll();

        return http.build();
    }
}
