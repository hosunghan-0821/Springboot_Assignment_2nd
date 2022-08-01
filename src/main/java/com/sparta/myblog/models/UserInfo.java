package com.sparta.myblog.models;

import com.sparta.myblog.Dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UserInfo {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;


    public UserInfo(SignupRequestDto requestDto){
        this.username = requestDto.getUserId();
        this.password = requestDto.getUserPassword();
        this.nickname = requestDto.getUserNickname();
    }
}
