package com.sparta.myblog.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    private Long postId;
    private String comment;
}
