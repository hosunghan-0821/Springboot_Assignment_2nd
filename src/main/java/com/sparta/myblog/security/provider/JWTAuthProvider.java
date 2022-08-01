package com.sparta.myblog.security.provider;

import com.sparta.myblog.models.UserInfo;
import com.sparta.myblog.repository.UserRepository;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.security.jwt.JwtDecoder;
import com.sparta.myblog.security.jwt.JwtPreProcessingToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component

public class JWTAuthProvider implements AuthenticationProvider {
    private final JwtDecoder jwtDecoder;

    private final UserRepository userRepository;

    @Autowired
    public JWTAuthProvider (JwtDecoder jwtDecoder,UserRepository userRepository){
        this.jwtDecoder=jwtDecoder;
        this.userRepository=userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        System.out.println(token);
        String username = jwtDecoder.decodeUsername(token);


        // TODO: API 사용시마다 매번 User DB 조회 필요
        //  -> 해결을 위해서는 UserDetailsImpl 에 User 객체를 저장하지 않도록 수정
        //  ex) UserDetailsImpl 에 userId, username, role 만 저장
        //    -> JWT 에 userId, username, role 정보를 암호화/복호화하여 사용
        UserInfo userInfo = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("해당 아이디 없어요"));
        UserDetailsImpl userDetails = new UserDetailsImpl(userInfo);
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtPreProcessingToken.class.isAssignableFrom(authentication);
    }
}
