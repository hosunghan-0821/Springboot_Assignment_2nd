package com.sparta.myblog.service;


import com.sparta.myblog.Dto.SignupRequestDto;
import com.sparta.myblog.models.UserInfo;
import com.sparta.myblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder= passwordEncoder;
    }

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



}
