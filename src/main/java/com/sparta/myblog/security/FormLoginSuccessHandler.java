package com.sparta.myblog.security;

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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
       final UserDetailsImpl userDetails = ( (UserDetailsImpl) authentication.getPrincipal());
       final String token = JwtTokenUtils.generateJwtToken(userDetails);
       response.addHeader(AUTH_HEADER,TOKEN_TYPE+" "+token);
       //final String token = JwtTokenUtils.generateJwtToken(userDetails);
    }
}
