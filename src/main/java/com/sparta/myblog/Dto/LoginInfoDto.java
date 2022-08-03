package com.sparta.myblog.Dto;


import com.sparta.myblog.models.UserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginInfoDto {
    private boolean success;
    private Object data;
    private String error;

    public LoginInfoDto(UserInfo userInfo){
        userInfo.setPassword(null);
        this.data=userInfo;
        success=true;
    }
}

