package com.sparta.myblog.service;


import com.sparta.myblog.Dto.SignupRequestDto;
import com.sparta.myblog.Dto.TokenDto;
import com.sparta.myblog.Dto.TokenRequestDto;
import com.sparta.myblog.models.RefreshToken;
import com.sparta.myblog.models.UserInfo;
import com.sparta.myblog.repository.RefreshTokenRepository;
import com.sparta.myblog.repository.UserRepository;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.security.jwt.HeaderTokenExtractor;
import com.sparta.myblog.security.jwt.JwtDecoder;
import com.sparta.myblog.security.jwt.JwtTokenUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.sparta.myblog.security.FormLoginSuccessHandler.TOKEN_TYPE;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final JwtDecoder jwtDecoder;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    private final HeaderTokenExtractor headerTokenExtractor;

    @Resource(name="userDetailsServiceImpl")
    private UserDetailsService userDetailsService;





    public void registerUser(SignupRequestDto requestDto){

        //여기서 요구조건 확인하는
        if(!checkSignupValueCondition(requestDto)){
            throw new IllegalArgumentException("회원가입 정보가 정확하지 않습니다.");
        };
        //회원 닉네임 중복 확인
        Optional<UserInfo> found = userRepository.findByUsername(requestDto.getUserId());
        if(found.isPresent()){
            throw new IllegalArgumentException("중복된 사용자 id가 존재합니다.");
        }
        requestDto.setUserPassword(passwordEncoder.encode(requestDto.getUserPassword())) ;
        UserInfo userInfo = new UserInfo(requestDto);
        userRepository.save(userInfo);


    }

    public boolean checkSignupValueCondition(SignupRequestDto requestDto){
        String nickname = requestDto.getUserNickname();
        String Id = requestDto.getUserId();
        String pw = requestDto.getUserPassword();
        String pwCheck = requestDto.getUserPasswordCheck();

        boolean checkValueCondition = true;
        String pattern = "^[a-zA-Z0-9]*$";
        if( !(Pattern.matches(pattern,Id) && Id.length()>=4 && Id.length()<=12) ){
            System.out.println("닉네임 문제");
            checkValueCondition=false;
        }
        else if( !(Pattern.matches(pattern,pw) && pw.length()>=4 && pw.length()<=32) ){
            System.out.println("비번 문제");
            checkValueCondition=false;
        }
        else if( !pw.equals(pwCheck) ){
            System.out.println("비번 중복 문제");
            checkValueCondition=false;
        }
        return checkValueCondition;

    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto){
        //0 refresh token extract header
        String refreshTokenStr =  headerTokenExtractor.extract(tokenRequestDto.getRefreshToken());
        String tokenStr = headerTokenExtractor.extract(tokenRequestDto.getToken());
        // 1.Refresh Token 검증
        if(!jwtDecoder.isValidRefreshToken(refreshTokenStr)){
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }
        //2. Access Token 으로부터 아이디 가져오기
        String username= jwtDecoder.decodeUsername(tokenStr);

        //3 저장소에서 member id를 기반으로 refresh token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(username)
                .orElseThrow(()->new RuntimeException("로그아웃 된 사용자입니다."));

        //Refresh Token 일치하는지 검사
        if(!refreshTokenStr.equals(refreshToken.getValue())){
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다");
        }
        UserDetailsImpl userDetails =(UserDetailsImpl) userDetailsService.loadUserByUsername(username);

        //새로운 토큰 생성
        String newAccessToken = JwtTokenUtils.generateJwtToken(userDetails);
        String newRefreshToken = JwtTokenUtils.generateJwtRefreshToken();
        TokenDto tokenDto = new TokenDto(TOKEN_TYPE+" "+newAccessToken,TOKEN_TYPE+" "+newRefreshToken);

        //저장소 정보 업데이트
        refreshToken.updateValue(newRefreshToken);

        return tokenDto;
    }



}
