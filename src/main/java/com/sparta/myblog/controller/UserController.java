package com.sparta.myblog.controller;

import com.sparta.myblog.Dto.SignupRequestDto;
import com.sparta.myblog.Dto.UserInfoDto;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/api/loginView")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/api/signup")
    public String signupPage(){
        return "signup";
    }

    @PostMapping("/api/signup")
    @ResponseBody
    public SignupRequestDto registerUser(@ModelAttribute SignupRequestDto signupRequestDto){
        System.out.println(signupRequestDto.getUserId()+"\n"+signupRequestDto.getUserNickname());
        userService.registerUser(signupRequestDto);
        return signupRequestDto;
    }

    @PostMapping("/api/userinfo")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails!=null){
            String username = userDetails.getUser().getUsername();
            System.out.println("로그인 된 상태");
            return new UserInfoDto(username);
        }
        return new UserInfoDto();



    }


}
