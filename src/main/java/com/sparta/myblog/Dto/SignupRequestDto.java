package com.sparta.myblog.Dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    private String userId;
    private String userPassword;
    private String userPasswordCheck;
    private String userNickname;

}
