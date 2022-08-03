package com.sparta.myblog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.myblog.Dto.LoginInfoDto;
import com.sparta.myblog.security.jwt.JwtTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        final UserDetailsImpl userDetails = ( (UserDetailsImpl) authentication.getPrincipal());
        final String token = JwtTokenUtils.generateJwtToken(userDetails);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        // loginInfoDto 객체 생성

        LoginInfoDto loginInfoDto = new LoginInfoDto(userDetails.getUser());

        // json 형태로 바꾸기
        String result = mapper.writeValueAsString(loginInfoDto);
        response.getWriter().write(result);
        response.addHeader(AUTH_HEADER,TOKEN_TYPE+" "+token);
       //final String token = JwtTokenUtils.generateJwtToken(userDetails);
    }
}
