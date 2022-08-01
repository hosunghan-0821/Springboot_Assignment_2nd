package com.sparta.myblog.controller;

import com.sparta.myblog.Dto.SignupRequestDto;
import com.sparta.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/api/login")
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


}
