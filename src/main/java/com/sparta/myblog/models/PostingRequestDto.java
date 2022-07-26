package com.sparta.myblog.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostingRequestDto {
    //title contents
    private String title;
    private String contents;
    private String writer;
    private String password;
}
