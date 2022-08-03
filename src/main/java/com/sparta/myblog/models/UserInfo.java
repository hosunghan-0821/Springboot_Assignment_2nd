package com.sparta.myblog.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.myblog.Dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Setter
public class UserInfo extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false)
    private String password;

    @Column
    private String nickname;


    public UserInfo(SignupRequestDto requestDto){
        this.username = requestDto.getUserId();
        this.password = requestDto.getUserPassword();
        this.nickname = requestDto.getUserNickname();
    }
}
