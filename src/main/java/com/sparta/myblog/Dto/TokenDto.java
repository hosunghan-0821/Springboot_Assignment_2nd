package com.sparta.myblog.Dto;

import lombok.Getter;

@Getter
public class TokenDto {
    private String token;
    private String refreshToken;

    public TokenDto (String token, String refreshToken){
        this.token=token;
        this.refreshToken=refreshToken;
    }
}
