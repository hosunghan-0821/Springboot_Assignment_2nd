package com.sparta.myblog.Dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenRequestDto {

    private String token;
    private String refreshToken;
}
